package bookManagement.domain.internal.book.model

import bookManagement.domain.internal.category.model.CategoryId
import org.joda.time.DateTime

case class BookImpl(
  identifier: BookId = BookId(0),
  name: String,
  author: String,
  category: CategoryId,
  purchasedDate: DateTime,
  description: Option[String],
  status: Status,
  publisher: String,
  isActive: Boolean,
  override val createdAt: Option[DateTime],
  override val updatedAt: Option[DateTime]) extends Book
