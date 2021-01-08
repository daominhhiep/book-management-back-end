package bookManagement.domain.internal.borrowingHistory.model

import bookManagement.domain.internal.book.model.BookId
import bookManagement.domain.internal.user.model.UserId
import org.joda.time.DateTime

case class BorrowingHistoryImpl(
  identifier: BorrowingId = BorrowingId(0),
  userId: UserId,
  bookId: BookId,
  startDate: DateTime,
  endDate: DateTime,
  returnDate: Option[DateTime],
  isActive: Boolean,
  overdueReason: Option[String],
  override val createdAt: Option[DateTime],
  override val updatedAt: Option[DateTime]) extends BorrowingHistory {

  override def update(obj: BorrowingHistory): BorrowingHistory = {
    BorrowingHistory (
      identifier = obj.identifier,
      userId = obj.userId,
      bookId = obj.bookId,
      startDate = obj.startDate,
      endDate = obj.endDate,
      returnDate = obj.returnDate,
      isActive = true,
      overdueReason = obj.overdueReason,
      createdAt = obj.createdAt,
      updatedAt = Option(new DateTime())
    )
  }
}
