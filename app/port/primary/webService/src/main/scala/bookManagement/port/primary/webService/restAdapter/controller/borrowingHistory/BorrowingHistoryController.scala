package bookManagement.port.primary.webService.restAdapter.controller.borrowingHistory

import bookManagement.application.feature.service.borrowingHistory.BorrowingHistoryService
import bookManagement.port.primary.webService.restAdapter.controller.APIController
import bookManagement.port.primary.webService.restAdapter.dto.ErrorCode.EntityNotFoundError
import bookManagement.port.primary.webService.restAdapter.dto.WarningParameter
import bookManagement.port.primary.webService.restAdapter.dto.borrowingHistory.BorrowingHistoryResponse
import bookManagement.port.primary.webService.restAdapter.form.BorrowingHistoryForm
import bookManagement.utility.exception.EntityNotFoundException
import javax.inject.Inject
import play.api.mvc._

import scala.util.{Failure, Success}

class BorrowingHistoryController @Inject()(cc: ControllerComponents, borrowingHistoryService: BorrowingHistoryService) extends APIController(cc) {

  def list: Action[AnyContent] = Action {
    success(BorrowingHistoryResponse(borrowingHistoryService.getAll.get))
  }

  def getByBookId(bookId: Int): Action[AnyContent] = Action {
    borrowingHistoryService.getByBookId(bookId)
      .map(borrowingHistories => success(BorrowingHistoryResponse(borrowingHistories.reverse)))
      .recover {
        case _: EntityNotFoundException => badRequestWarning(WarningParameter("warn.entityNotFound", "warn.entityNotFound", "Can't found to update"))
        case e: Exception => badRequestWarning(WarningParameter("", "", e.getMessage))
      }.get
  }

  def create: Action[AnyContent] = Action { implicit request =>
    BorrowingHistoryForm.form.bindFromRequest.fold(
      formWithError => badRequestWarning(formWithError.errors),
      borrowingHistory => {
        val result = for {
          status <- borrowingHistoryService.create(borrowingHistory)
        } yield status

        result match {
          case Success(status) =>
            success(BorrowingHistoryResponse(borrowingHistory))
          case Failure(e) => e match {
            case _ => badRequestWarning(WarningParameter("warn.createError", "warn.createError", e.getMessage))
          }
        }
      })
  }

  def update: Action[AnyContent] = Action { implicit request =>
    BorrowingHistoryForm.form.bindFromRequest.fold(
      formWithError => badRequestWarning(formWithError.errors),
      borrowingHistory => {
        val result = for {
          status <- borrowingHistoryService.update(borrowingHistory)
        } yield status

        result match {
          case Success(status) => success()
          case Failure(e) => e match {
            case _: EntityNotFoundException => badRequestWarning(WarningParameter("warn.entityNotFound", "warn.entityNotFound", "Can't found to update"))
            case _ => badRequestWarning(WarningParameter("warn.updateError", "warn.updateError", e.getMessage))
          }
        }
      })
  }

  def delete(id: Int): Action[AnyContent] = Action {
    val result = for {
      status <- borrowingHistoryService.delete(id)
    } yield status

    result match {
      case Success(status) => success()
      case Failure(e) => e match {
        case _: EntityNotFoundException => badRequestWarning(WarningParameter("warn.entityNotFound", "warn.entityNotFound", "Can't found to update"))
        case _ => badRequestWarning(WarningParameter("warn.deleteError", "warn.deleteError", e.getMessage))
      }
    }
  }
}
