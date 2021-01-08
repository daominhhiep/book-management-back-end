package bookManagement.port.primary.webService.restAdapter.form

import bookManagement.domain.internal.book.model.{Book, BookId, Status}
import bookManagement.domain.internal.category.model.CategoryId
import org.joda.time.DateTime
import play.api.data.Form
import play.api.data.Forms._

object BookForm {
  val categoryMapping =
    number.transform((id: Int) => CategoryId(id), (id: CategoryId) => id.value)

  val datetimeMapping =
    nonEmptyText.transform(DateTime.parse, (date: DateTime) => date.toString("yyyy-MM-dd"))

  val statusMapping =
    nonEmptyText.verifying("Status is invalid", Status(_).isDefined).transform(Status(_).get, (status: Status) => status.value)

  val idMapping =
    optional(number).transform((id: Option[Int]) => BookId(id.getOrElse(0)), (id: BookId) => Some(id.value))

  val form = Form(
    mapping(
      "id" -> idMapping,
      "name" -> nonEmptyText,
      "author" -> nonEmptyText,
      "category" -> categoryMapping,
      "purchasedDate" -> datetimeMapping,
      "description" -> optional(text),
      "status" -> statusMapping,
      "publisher" -> nonEmptyText,
      "isActive" -> boolean,
      "createdAt" -> optional(datetimeMapping),
      "updatedAt" -> optional(datetimeMapping))(Book.apply)(Book.unapply))
}
