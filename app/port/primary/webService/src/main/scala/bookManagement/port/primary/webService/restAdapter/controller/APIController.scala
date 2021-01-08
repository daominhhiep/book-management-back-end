package bookManagement.port.primary.webService.restAdapter.controller

import bookManagement.port.primary.webService.restAdapter.dto.{ APIResult, WarningParameter }
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.write
import play.api.data.FormError
import play.api.libs.json.JsValue
import play.api.mvc._

abstract class APIController(cc: ControllerComponents) extends AbstractController(cc) {

  implicit val formats = DefaultFormats

  protected def success[T](result: T): Result =
    Ok(write(APIResult.toSuccessJson(result)))

  protected def success(result: JsValue): Result =
    Ok(APIResult.toSuccess(result))

  protected def success[T](result: Seq[T]): Result =
    Ok(write(APIResult.toSuccessJson(result)))

  protected def failure[T](result: Seq[T]): Result =
    Ok(write(APIResult.toFailureJson(result)))

  protected def success(): Result =
    Ok(write(APIResult.toSuccessJson))

  protected def badRequestWarning(formErrors: Seq[FormError]): Result =
    BadRequest(write(APIResult.toWarningJson(formErrors.map { formError =>
      {
        val errorKey: String = if (formError.message.nonEmpty && formError.message != "error.required") { formError.message } else { formError.key }
        WarningParameter(
          errorKey,
          "warn." + errorKey + ".code",
          "warn." + errorKey)
      }
    })))

  protected def badRequestFormWarning(formErrors: Seq[FormError]): Result =
    BadRequest(write(APIResult.toWarningJson(formErrors.map { formError =>
      {
        WarningParameter(
          formError.key,
          "warn." + formError.key + ".code",
          "warn." + formError.key)
      }
    })))

  protected def badRequestWarning(formErrors: Seq[FormError], code: String, message: String): Result =
    BadRequest(write(APIResult.toWarningJson(formErrors.map { formError =>
      WarningParameter(
        formError.key,
        code,
        message)
    })))

  protected def badRequestWarning(warning: WarningParameter): Result =
    BadRequest(write(APIResult.toWarningJson(Seq(warning))))

  protected def error[T](status: Status, code: Int, message: String): Result =
    status(write(APIResult.toErrorJson(code, message)))

  protected def unauthorizedError(code: Int, message: String): Result =
    Results.Unauthorized(write(APIResult.toErrorJson(code, message)))
}

