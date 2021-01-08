package bookManagement.port.primary.webService.restAdapter.controller.book

import bookManagement.application.feature.service.book.BookService
import bookManagement.port.primary.webService.restAdapter.controller.APIController
import bookManagement.port.primary.webService.restAdapter.dto.WarningParameter
import bookManagement.port.primary.webService.restAdapter.dto.book.BookResponse
import bookManagement.port.primary.webService.restAdapter.form.BookForm
import bookManagement.utility.exception.EntityNotFoundException
import javax.inject.Inject
import play.api.mvc._

import scala.util.{Failure, Success}

class BookController @Inject()(cc: ControllerComponents, bookService: BookService) extends APIController(cc) {

  def list: Action[AnyContent] = Action {
    success(BookResponse(bookService.getAll().get))
  }

  def findById(id: Int): Action[AnyContent] = Action {
    val result = for {
      book <- bookService.findById(id)
    } yield book

    result match {
      case Success(result) => success(BookResponse(result))
      case Failure(e) => e match {
        case _: EntityNotFoundException =>  badRequestWarning(WarningParameter("warn.entityNotFound", "warn.entityNotFound", "Can't found to update"))
        case _ =>  badRequestWarning(WarningParameter("warn.findByIdError", "warn.findByIdError", e.getMessage))
      }
    }
  }

  def create: Action[AnyContent] = Action { implicit request =>
    BookForm.form.bindFromRequest.fold(
      formWithError => badRequestWarning(formWithError.errors),
      book => {
        (for {
          _ <- bookService.createBook(book)
        } yield {
          success(BookResponse(book))
        }).get
      })
  }

  def update: Action[AnyContent] = Action { implicit request =>
    BookForm.form.bindFromRequest.fold(
      formWithError => badRequestWarning(formWithError.errors),
      book => {
        (for {
          _ <- bookService.updateBook(book)
        } yield {
          success(BookResponse(book))
        }).get
      })
  }

  def delete(id: Int): Action[AnyContent] = Action {
    val result = for {
      status <- bookService.deleteBook(id)
    } yield status

    result match {
      case Success(status) =>
        success()
      case Failure(e) => e match {
        case _ =>  badRequestWarning(WarningParameter("warn.deleteError", "warn.deleteError", e.getMessage))
      }
    }
  }
}
