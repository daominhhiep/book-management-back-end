package bookManagement.domain.internal.borrowingHistory.model

import bookManagement.domain.internal.book.model.BookId
import bookManagement.domain.internal.user.model.UserId
import bookManagement.utility.model.Entity
import org.joda.time.DateTime

trait BorrowingHistory extends Entity[BorrowingId] with BorrowingHistoryFields {
  def update(obj: BorrowingHistory) : BorrowingHistory
}

object BorrowingHistory {
  def apply(
             identifier: BorrowingId,
             userId: UserId,
             bookId: BookId,
             startDate: DateTime,
             endDate: DateTime,
             returnDate: Option[DateTime],
             isActive: Boolean,
             overdueReason: Option[String],
             createdAt: Option[DateTime],
             updatedAt: Option[DateTime]): BorrowingHistory = BorrowingHistoryImpl(
    identifier,
    userId,
    bookId,
    startDate,
    endDate,
    returnDate,
    isActive,
    overdueReason,
    createdAt,
    updatedAt)

  def unapply(arg: BorrowingHistory) = None
}
