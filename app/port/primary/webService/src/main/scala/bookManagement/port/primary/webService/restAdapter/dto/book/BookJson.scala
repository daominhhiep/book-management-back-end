package bookManagement.port.primary.webService.restAdapter.dto.book

import bookManagement.domain.internal.book.model.Book

case class BookJson(
  id: Option[Int],
  name: String,
  author: String,
  category: Int,
  purchasedDate: String,
  description: Option[String],
  status: String,
  isActive: Boolean,
  publisher: String,
  createdAt: Option[String],
  updatedAt: Option[String])

object BookResponse {

  def apply(books: Seq[Book]): Seq[BookJson] = books.map(BookResponse(_))

  def apply(book: Book): BookJson = BookJson(
    id = Some(book.identifier.value),
    name = book.name,
    author = book.author,
    category = book.category.value,
    purchasedDate = book.purchasedDate.toString("yyyy-MM-dd"),
    description = book.description,
    status = book.status.value,
    isActive = book.isActive,
    publisher = book.publisher,
    createdAt = book.createdAt.map(_.toString("dd-MM-yyyy")),
    updatedAt = book.updatedAt.map(_.toString("dd-MM-yyyy")))
}
