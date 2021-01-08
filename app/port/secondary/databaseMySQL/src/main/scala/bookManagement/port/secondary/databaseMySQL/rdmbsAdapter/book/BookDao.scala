package bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.book

import bookManagement.domain.internal.book.model.BookFields
import bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.CRUDMapperWithId
import scalikejdbc._

private[book] class BookDao extends CRUDMapperWithId[Int, BookFields, BookRecord]{

  override lazy val tableName = "book"
  override lazy val defaultAlias = createAlias("book")
  override lazy val primaryKeyFieldName = "book_id"

  override def columnNames: Seq[String] = Seq(
    "book_id",
    "status",
    "name",
    "author",
    "description",
    "purchased_date",
    "category_id",
    "publisher",
    "is_active",
    "created_at",
    "updated_at"
  )

  override def toNamedValues(record: BookRecord): Seq[(Symbol, Any)] = Seq(
    'book_id          -> record.bookId,
    'status           -> record.status,
    'name             -> record.name,
    'author           -> record.author,
    'description      -> record.description,
    'purchased_date   -> record.purchasedDate,
    'category_id      -> record.categoryId,
    'publisher        -> record.publisher,
    'is_active        -> record.isActive,
    'created_at       -> record.createdAt,
    'updated_at       -> record.updatedAt
  )

  override def idToRawValue(id: Int): Int = id
  override def rawValueToId(value: Any): Int = value.asInstanceOf[Int]

  override def extract(rs: WrappedResultSet, n: ResultName[BookRecord]): BookRecord = {
    BookRecord(
      bookId        = rs.get(n.bookId),
      status        = rs.get(n.status),
      name          = rs.get(n.name),
      author        = rs.get(n.author),
      description   = rs.get(n.description),
      purchasedDate = rs.get(n.purchasedDate),
      categoryId    = rs.get(n.categoryId),
      publisher     = rs.get(n.publisher),
      isActive      = rs.get(n.isActive),
      createdAt     = rs.get(n.createdAt),
      updatedAt     = rs.get(n.updatedAt)
    )
  }
}
