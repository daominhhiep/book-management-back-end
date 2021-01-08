package bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.book

import bookManagement.utility.model.Record
import org.joda.time.DateTime

case class BookRecord(
  bookId: Int,
  status: String,
  name: String,
  author: String,
  description: Option[String],
  purchasedDate: DateTime,
  categoryId: Int,
  publisher: String,
  isActive: Boolean,
  override val createdAt: Option[DateTime],
  override val updatedAt: Option[DateTime],
) extends Record
