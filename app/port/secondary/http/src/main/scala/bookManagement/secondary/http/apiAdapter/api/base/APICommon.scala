package bookManagement.secondary.http.apiAdapter.api.base

import play.api.libs.ws.WSAuthScheme

sealed abstract class Method(val value: String)

object Method {
  case object Get extends Method("GET")
  case object Post extends Method("POST")
  case object Put extends Method("PUT")
  case object Delete extends Method("DELETE")
}

sealed abstract class Header(val value: (String, String))

object Header {
  case object ContentJson extends Header("Content-Type" -> "application/json")
}

abstract class Authentication {
  val scheme: WSAuthScheme
}

case class BasicAuth(
  username: String,
  password: String,
  override val scheme: WSAuthScheme = WSAuthScheme.BASIC) extends Authentication

object APICommon {
  val httpInfo = (method: String, url: String, payload: String, requestParam: String, response: String) =>
    s"Execute Http Request with method: ${method}, url: ${url}, requestParam: ${requestParam}, payload: ${payload}, response: ${response}"
}

