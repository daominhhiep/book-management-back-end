package bookManagement.port.primary.webService.restAdapter.common

import bookManagement.port.primary.webService.restAdapter.dto.{ APIResult, ErrorCode }
import bookManagement.utility.exception.EntityNotFoundException
import javax.inject._
import org.json4s._
import org.json4s.jackson.Serialization.write
import play.api.http.DefaultHttpErrorHandler
import play.api.mvc.Results._
import play.api.mvc._
import play.api.routing.Router
import play.api.{ Configuration, Environment, OptionalSourceMapper }

import scala.concurrent._

@Singleton
class ErrorHandler @Inject() (
  env: Environment,
  config: Configuration,
  sourceMapper: OptionalSourceMapper,
  router: Provider[Router]) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {
  implicit val formats = DefaultFormats

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful(
      BadRequest(
        write(APIResult.toErrorJson(
          code = ErrorCode.ParameterFormatError.value,
          message = "Parameter format error"))))
  }

  override def onServerError(request: RequestHeader, ex: Throwable): Future[Result] = {
    Future.successful(
      ex match {
        case _: EntityNotFoundException =>
          NotFound(
            write(APIResult.toErrorJson(
              code = ErrorCode.EntityNotFoundError.value,
              message = "Entity Not Found")))
        case _ => {
          InternalServerError(
            write(APIResult.toErrorJson(
              code = ErrorCode.InternalServerError.value,
              message = ex.getMessage)))
        }
      })
  }

  override def onForbidden(request: RequestHeader, message: String): Future[Result] = {
    Future.successful(
      Forbidden(
        write(APIResult.toErrorJson(
          code = ErrorCode.ForbiddenError.value,
          message = "Not allowed to access this resource"))))
  }
}
