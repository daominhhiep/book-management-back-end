package bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.borrowingHistory

import bookManagement.domain.internal.book.model._
import bookManagement.domain.internal.borrowingHistory.BorrowingHistoryRepository
import bookManagement.domain.internal.borrowingHistory.model.{BorrowingHistory, BorrowingHistoryFields, BorrowingId}
import bookManagement.domain.internal.user.model.UserId
import bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.BasicWithIdFeatureRepositoryOnJDBC
import bookManagement.utility.exception.EntityNotFoundException
import org.joda.time.DateTime
import scalikejdbc.{SQL, sqls}

import scala.util.Try

class BorrowingHistoryRepositoryOnJDBCImpl(override protected[this] val dao: BorrowingHistoryDao = new BorrowingHistoryDao)
  extends BorrowingHistoryRepository with BasicWithIdFeatureRepositoryOnJDBC[BorrowingId, BorrowingHistoryFields, BorrowingHistory, Int, BorrowingHistoryRecord] {

  override type DAO = BorrowingHistoryDao

  override protected def convertToEntity(record: BorrowingHistoryRecord): BorrowingHistory = {
    BorrowingHistory(
      identifier = BorrowingId(record.borrowingId),
      userId = UserId(record.userId),
      bookId = BookId(record.bookId),
      startDate = record.startDate,
      endDate = record.endDate,
      returnDate = record.returnDate,
      overdueReason = record.overdueReason,
      isActive = record.isActive,
      createdAt = record.createdAt,
      updatedAt = record.updatedAt
    )
  }

  override protected def fieldsFromNamedValues(fields: BorrowingHistoryFields): Seq[(Symbol, Any)] = Seq(
    'userId -> fields.userId.value,
    'bookId -> fields.bookId.value,
    'startDate -> fields.startDate,
    'endDate -> fields.endDate,
    'returnDate -> fields.returnDate,
    'overdueReason -> fields.overdueReason,
    'is_active -> fields.isActive,
    'created_at -> fields.createdAt,
    'updated_at -> fields.updatedAt
  )

  override def convertToIdentifier(id: Int): BorrowingId = BorrowingId(id)

  override protected def convertToRecordId(id: BorrowingId): Int = id.value

  protected def toValues(fields: BorrowingHistoryFields): Seq[Any] = fieldsFromNamedValues(fields).map(v => v._2)

  override def bulkCreateOrUpdate(fields: Seq[BorrowingHistoryFields]): Try[Int] = {
    withDBSession { implicit session =>
      dao.bulkCreateOrUpdate(
        Seq(
          dao.column.userId,
          dao.column.bookId,
          dao.column.startDate,
          dao.column.endDate,
          dao.column.returnDate,
          dao.column.overdueReason,
          dao.column.isActive,
          dao.column.createdAt,
          dao.column.updatedAt
        ),
        fields,
        Seq(
          dao.column.userId,
          dao.column.bookId,
          dao.column.startDate,
          dao.column.endDate,
          dao.column.returnDate,
          dao.column.overdueReason,
          dao.column.isActive,
          dao.column.createdAt,
          dao.column.updatedAt
        ),
        toValues
      )
    }
  }

  def resolveAllActive(): Try[Seq[BorrowingHistory]] = withDBSession { implicit session =>
    dao.where(sqls.eq(dao.column.isActive, 1)).apply().map(convertToEntity)
  }

  def resolveByBookId(bookId: Int): Try[Seq[BorrowingHistory]] = withDBSession { implicit session =>
    dao.where(sqls.eq(dao.column.isActive, 1).and.eq(dao.column.bookId, bookId).orderBy()).apply().map(convertToEntity)
  }

  def update(borrowingHistory: BorrowingHistory): Try[Int] =
    withDBSession { implicit session =>
      val clause = SQL(
        "UPDATE borrowing_history " +
          "SET user_id = {userId}" +
          ", start_date = {startDate}" +
          ", end_date = {endDate}" +
          ", return_date = {returnDate}" +
          ", overdue_reason = {overdueReason}" +
          ", updated_at = {updatedAt}" +
          " WHERE borrowing_id = {id}")
      clause.bindByName(
        'userId -> borrowingHistory.userId.value,
        'startDate -> borrowingHistory.startDate,
        'endDate -> borrowingHistory.endDate,
        'returnDate -> borrowingHistory.returnDate,
        'overdueReason -> borrowingHistory.overdueReason,
        'updatedAt -> borrowingHistory.updatedAt,
        'id -> borrowingHistory.identifier.value).update().apply()
    }

  def delete(id: Int): Try[Int] =
    withDBSession { implicit session =>
      val clause = SQL("UPDATE borrowing_history SET is_active = {isActive} WHERE borrowing_id = {id}")
      clause.bindByName('isActive -> 0, 'id -> id).update().apply()
    }

  override def findById(id: Int): Try[BorrowingHistory] = withDBSession { implicit session =>
    val entity = dao.findBy(sqls.eq(dao.column.borrowingId, id).and.eq(dao.column.isActive, 1)).map(convertToEntity)
    entity.getOrElse(throw new EntityNotFoundException(s"models = ${dao.tableName}, id = $id"))
  }
}
