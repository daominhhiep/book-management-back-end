package bookManagement.secondary.http.apiAdapter.api.base

import org.json4s.JValue
import play.api.libs.json.JsValue

import scala.concurrent.duration._
import scala.language.postfixOps

trait APIConfigurationBase[T] {

  val timeout = 1 minutes

  val method: Method

  val url: String

  val payload: Option[T]

  val headers: Seq[(String, String)] = Seq(Header.ContentJson.value)

  val queryStringParameters: Seq[(String, String)] = Seq.empty

  val isLogResult: Boolean = true // false if don't write http response to log
}

abstract class APIConfigurationBaseString extends APIConfigurationBase[String] {
  override val payload: Option[String] = None
}

abstract class APIConfigurationBaseJsValue extends APIConfigurationBase[JsValue] {
  override val payload: Option[JsValue] = None
}

abstract class APIConfigurationBaseJValue extends APIConfigurationBase[JValue] {
  override val payload: Option[JValue] = None
}

