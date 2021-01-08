package bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.borrowingHistory

import bookManagement.domain.internal.borrowingHistory.model.BorrowingHistoryFields
import bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.CRUDMapperWithId
import scalikejdbc._

private[borrowingHistory] class BorrowingHistoryDao extends CRUDMapperWithId[Int, BorrowingHistoryFields, BorrowingHistoryRecord] {

  override lazy val tableName = "borrowing_history"
  override lazy val defaultAlias = createAlias("borrowing")
  override lazy val primaryKeyFieldName = "borrowing_id"

  override def columnNames: Seq[String] = Seq(
    "borrowing_id",
    "user_id",
    "book_id",
    "start_date",
    "end_date",
    "return_date",
    "overdue_reason",
    "is_active",
    "created_at",
    "updated_at"
  )

  override def toNamedValues(record: BorrowingHistoryRecord): Seq[(Symbol, Any)] = Seq(
    'borrowing_id -> record.borrowingId,
    'user_id -> record.userId,
    'book_id -> record.bookId,
    'start_date -> record.startDate,
    'end_date -> record.endDate,
    'return_date -> record.returnDate,
    'overdue_reason -> record.overdueReason,
    'is_active -> record.isActive,
    'created_at -> record.createdAt,
    'updated_at -> record.updatedAt
  )

  override def idToRawValue(id: Int): Int = id

  override def rawValueToId(value: Any): Int = value.asInstanceOf[Int]

  override def extract(rs: WrappedResultSet, n: ResultName[BorrowingHistoryRecord]): BorrowingHistoryRecord = {
    BorrowingHistoryRecord(
      borrowingId = rs.get(n.borrowingId),
      userId = rs.get(n.userId),
      bookId = rs.get(n.bookId),
      startDate = rs.get(n.startDate),
      endDate = rs.get(n.endDate),
      returnDate = rs.get(n.returnDate),
      overdueReason = rs.get(n.overdueReason),
      isActive = rs.get(n.isActive),
      createdAt = rs.get(n.createdAt),
      updatedAt = rs.get(n.updatedAt)
    )
  }
}
