package bookManagement.domain.internal.borrowingHistory.model

import bookManagement.domain.internal.book.model.BookId
import bookManagement.domain.internal.user.model.UserId
import bookManagement.utility.model.Fields
import org.joda.time.DateTime

trait BorrowingHistoryFields extends Fields {
  val userId: UserId
  val bookId: BookId
  val startDate: DateTime
  val endDate: DateTime
  val returnDate: Option[DateTime]
  val isActive: Boolean
  val overdueReason: Option[String]
  val updatedAt: Option[DateTime]
  val createdAt: Option[DateTime]
}
