package bookManagement.port.primary.webService.restAdapter.dto.borrowingHistory

import bookManagement.domain.internal.borrowingHistory.model.BorrowingHistory

case class BorrowingHistoryJson(
                                 id: Option[Int],
                                 userId: Int,
                                 bookId: Int,
                                 startDate: String,
                                 endDate: String,
                                 returnDate: Option[String],
                                 overdueReason: Option[String],
                                 isActive: Boolean,
                                 createdAt: Option[String],
                                 updatedAt: Option[String])

object BorrowingHistoryResponse {

  def apply(borrowingHistories: Seq[BorrowingHistory]): Seq[BorrowingHistoryJson] = borrowingHistories.map(BorrowingHistoryResponse(_))

  def apply(borrowingHistory: BorrowingHistory): BorrowingHistoryJson = BorrowingHistoryJson(
    id = Some(borrowingHistory.identifier.value),
    userId = borrowingHistory.userId.value,
    bookId = borrowingHistory.bookId.value,
    startDate = borrowingHistory.startDate.toString("yyyy-MM-dd"),
    endDate = borrowingHistory.endDate.toString("yyyy-MM-dd"),
    returnDate = borrowingHistory.returnDate.map(_.toString("yyyy-MM-dd")),
    overdueReason = borrowingHistory.overdueReason,
    isActive = borrowingHistory.isActive,
    createdAt = borrowingHistory.createdAt.map(_.toString("yyyy-MM-dd")),
    updatedAt = borrowingHistory.updatedAt.map(_.toString("yyyy-MM-dd")))
}
