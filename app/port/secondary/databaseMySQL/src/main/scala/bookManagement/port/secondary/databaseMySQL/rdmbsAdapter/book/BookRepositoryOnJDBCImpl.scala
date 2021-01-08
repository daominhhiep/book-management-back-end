package bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.book

import bookManagement.domain.internal.book.BookRepository
import bookManagement.domain.internal.book.model._
import bookManagement.domain.internal.category.model.CategoryId
import bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.BasicWithIdFeatureRepositoryOnJDBC
import bookManagement.utility.exception.EntityNotFoundException
import org.joda.time.DateTime
import scalikejdbc.{SQL, sqls}

import scala.util.Try

class BookRepositoryOnJDBCImpl(override protected[this] val dao: BookDao = new BookDao)
  extends BookRepository with BasicWithIdFeatureRepositoryOnJDBC[BookId, BookFields, Book, Int, BookRecord] {

  override type DAO = BookDao

  override protected def convertToEntity(record: BookRecord): Book = {
    Book(
      identifier      = BookId(record.bookId),
      name            = record.name,
      author          = record.author,
      category        = CategoryId(record.categoryId),
      purchasedDate   = record.purchasedDate,
      description     = record.description,
      status          = Status.apply(record.status).get,
      publisher       = record.publisher,
      isActive        = record.isActive,
      createdAt       = record.createdAt,
      updatedAt       = record.updatedAt
    )
  }

  override protected def fieldsFromNamedValues(fields: BookFields): Seq[(Symbol, Any)] = Seq(
    'status         -> fields.status.value,
    'name           -> fields.name,
    'author         -> fields.author,
    'description    -> fields.description,
    'purchased_date -> fields.purchasedDate,
    'category_id    -> fields.category.value,
    'publisher      -> fields.publisher,
    'is_active      -> fields.isActive,
    'created_at     -> fields.createdAt,
    'updated_at     -> fields.updatedAt
  )

  override def convertToIdentifier(id: Int): BookId = BookId(id)

  override protected def convertToRecordId(id: BookId): Int = id.value

  protected def toValues(fields: BookFields): Seq[Any] = fieldsFromNamedValues(fields).map(v => v._2)

  override def bulkCreateOrUpdate(fields: Seq[BookFields]): Try[Int] = {
    withDBSession { implicit session =>
      dao.bulkCreateOrUpdate(
        Seq(
          dao.column.status,
          dao.column.name,
          dao.column.author,
          dao.column.description,
          dao.column.purchasedDate,
          dao.column.categoryId,
          dao.column.publisher,
          dao.column.isActive,
          dao.column.createdAt,
          dao.column.updatedAt
        ),
        fields,
        Seq(
          dao.column.status,
          dao.column.name,
          dao.column.author,
          dao.column.description,
          dao.column.purchasedDate,
          dao.column.categoryId,
          dao.column.publisher,
          dao.column.isActive,
          dao.column.createdAt,
          dao.column.updatedAt
        ),
        toValues
      )
    }
  }

  def resolveAllActive(): Try[Seq[Book]] = withDBSession { implicit session =>
    dao.where(sqls.eq(dao.column.isActive, 1)).apply().map(convertToEntity)
  }

  def updateBook(book: Book): Try[Int] =
    withDBSession { implicit session =>
      val clause = SQL(
        "UPDATE book " +
          "SET name = {name}" +
          ", author = {author}" +
          ", category_id = {category}" +
          ", purchased_date = {purchasedDate}" +
          ", publisher = {publisher}" +
          ", description = {description}" +
          ", updated_at = {updatedAt}" +
          " WHERE book_id = {id}")
      clause.bindByName(
        'name -> book.name,
        'author -> book.author,
        'category -> book.category.value,
        'purchasedDate -> book.purchasedDate,
        'publisher -> book.publisher,
        'description -> book.description,
        'updatedAt -> new DateTime(),
        'id -> book.identifier.value).update().apply()
    }

  def updateStatusBook(id: Int, status: Status): Try[Int] =
    withDBSession { implicit session =>
      val clause = SQL(
        "UPDATE book " +
          "SET status = {status}" +
          " WHERE book_id = {id}")
      clause.bindByName(
        'status -> status.value,
        'id -> id).update().apply()
    }

  def deleteBook(id: Int): Try[Int] =
    withDBSession { implicit session =>
      val clause = SQL("UPDATE book SET is_active = {isActive} WHERE book_id = {id}")
      clause.bindByName('isActive -> 0, 'id -> id).update().apply()
    }

  override def findById(id: Int): Try[Book] = withDBSession { implicit session =>
    val entity = dao.findBy(sqls.eq(dao.column.bookId, id).and.eq(dao.column.isActive, 1)).map(convertToEntity)
    entity.getOrElse(throw new EntityNotFoundException(s"models = ${dao.tableName}, id = $id"))
  }
}
