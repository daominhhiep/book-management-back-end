package bookManagement.domain.internal.book.model

import bookManagement.domain.internal.category.model.CategoryId
import bookManagement.utility.model.Entity
import org.joda.time.DateTime

trait Book extends Entity[BookId] with BookFields

object Book {
  def apply(
    identifier: BookId,
    name: String,
    author: String,
    category: CategoryId,
    purchasedDate: DateTime,
    description: Option[String],
    status: Status,
    publisher: String,
    isActive: Boolean,
    createdAt: Option[DateTime],
    updatedAt: Option[DateTime]): Book = BookImpl(
    identifier,
    name,
    author,
    category,
    purchasedDate,
    description,
    status,
    publisher,
    isActive,
    createdAt,
    updatedAt)

  def unapply(arg: Book) = None
}
