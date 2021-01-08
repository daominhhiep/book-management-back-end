package bookManagement.domain.internal.book.model

import bookManagement.domain.internal.category.model.CategoryId
import bookManagement.utility.model.Fields
import org.joda.time.DateTime

trait BookFields extends Fields {
  val name: String
  val author: String
  val category: CategoryId
  val purchasedDate: DateTime
  val description: Option[String]
  val status: Status
  val publisher: String
  val isActive: Boolean
  val updatedAt: Option[DateTime]
  val createdAt: Option[DateTime]
}
