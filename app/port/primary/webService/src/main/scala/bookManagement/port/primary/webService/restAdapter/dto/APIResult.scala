package bookManagement.port.primary.webService.restAdapter.dto

import play.api.libs.json.{ JsValue, Json }

case class WarningParameter(key: String, code: String, message: String)

case class ErrorParameter(code: Int, message: String)

case class APIResult(
  success: Boolean,
  result: Option[Any] = None,
  warning: Option[Seq[WarningParameter]] = None,
  error: Option[ErrorParameter] = None)

object APIResult {

  def toSuccessJson(result: Any): APIResult = {
    APIResult(
      success = true,
      Some(result))
  }

  def toSuccess(result: JsValue): JsValue =
    Json.obj("success" -> true, "result" -> result)

  def toError(result: JsValue, code: Int, message: String): JsValue =
    Json.toJson("success" -> false, "error" -> Json.obj("code" -> code, "message" -> message))

  def toSuccessJson: APIResult = {
    APIResult(
      success = true)
  }

  def toFailureJson(result: Any): APIResult = {
    APIResult(
      success = false,
      Some(result))
  }

  def toErrorJson(code: Int, message: String): APIResult =
    APIResult(
      success = false,
      result = None,
      warning = None,
      Some(ErrorParameter(code, message)))

  def toWarningJson(warnings: Seq[WarningParameter]): APIResult = {
    APIResult(
      success = false,
      None,
      Some(warnings),
      None)
  }
}
