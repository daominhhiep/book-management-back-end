package bookManagement.port.primary.webService.restAdapter.form

import bookManagement.domain.internal.book.model.{Book, BookId, Status}
import bookManagement.domain.internal.borrowingHistory.model.{BorrowingHistory, BorrowingId}
import bookManagement.domain.internal.category.model.CategoryId
import bookManagement.domain.internal.user.model.UserId
import org.joda.time.DateTime
import play.api.data.Form
import play.api.data.Forms._

object BorrowingHistoryForm {
  val userMapping = number.transform((id: Int) => UserId(id), (id: UserId) => id.value)
  val bookMapping = number.transform((id: Int) => BookId(id), (id: BookId) => id.value)
  val datetimeMapping =
    nonEmptyText.transform(DateTime.parse, (date: DateTime) => date.toString("yyyy-MM-dd"))

  val returnDateMapping =
    text.transform(DateTime.parse, (date: DateTime) => date.toString("yyyy-MM-dd"))

  val idMapping =
    optional(number).transform((id: Option[Int]) => BorrowingId(id.getOrElse(0)), (id: BorrowingId) => Some(id.value))

  val form = Form(
    mapping(
      "id" -> idMapping,
      "userId" -> userMapping,
      "bookId" -> bookMapping,
      "startDate" -> datetimeMapping,
      "endDate" -> datetimeMapping,
      "returnDate" -> optional(returnDateMapping),
      "isActive" -> boolean,
      "overdueReason" -> optional(text),
      "createdAt" -> optional(datetimeMapping),
      "updatedAt" -> optional(datetimeMapping))(BorrowingHistory.apply)(BorrowingHistory.unapply))
}