package bookManagement.port.primary.webService.restAdapter.controller.book

import bookManagement.application.feature.service.book.BookService
import bookManagement.domain.internal.book.BookRepository
import org.specs2.mock.Mockito
import play.api.mvc.Results
import play.api.test.{FakeRequest, Helpers, PlaySpecification, WithApplication}

import scala.util._

class BookControllerSpec extends PlaySpecification with Mockito with Results {
  sequential

  val bookService = mock[BookService]
  val bookRepository = mock[BookRepository]

  val bookController = new BookController(
    Helpers.stubControllerComponents(),
    bookService
  )
  "Get Book List Successfully" >> new WithApplication() {
    val request = FakeRequest(GET, "/api/books")
    bookService.getAll() returns (Success(Seq()))

    val result = bookController.list.apply(request)
    status(result) must equalTo(OK)
  }
    "Create Book Successfully" >> new WithApplication() {
      val request = FakeRequest(POST, "/api/books/").withFormUrlEncodedBody("name" -> "book 1", "author" -> "author 1", "category" -> "1", "purchasedDate" -> "1999-11-02", "status" -> "BORROWING")
      val results = bookController.create.apply(request)
      status(results) must equalTo(OK)
    }
}
