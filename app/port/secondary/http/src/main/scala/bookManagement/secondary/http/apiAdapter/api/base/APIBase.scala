package bookManagement.secondary.http.apiAdapter.api.base

import akka.util.ByteString
import bookManagement.utility.exception.JsonParserException
import bookManagement.utility.logger.Logger
import org.json4s._
import org.json4s.jackson.JsonMethods._
import play.api.libs.concurrent.CustomExecutionContext
import play.api.libs.json._
import play.api.libs.ws._

import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, Future }
import scala.util._

/**
 * @tparam T: Request Type
 * @tparam C: Configuration
 * @tparam R: Response Type
 */
trait APIBase[T, C <: APIConfigurationBase[T], R] {

  type Response = R
  type Configuration = C
  val ws: WSClient
  implicit val ec: CustomExecutionContext
  implicit val writeableT: BodyWritable[Option[T]]

  protected def parsePayload(payload: String): Try[Response]

  private def responseParser(wsResponse: WSResponse): RawResponse[Response] = {
    val headers = wsResponse.headers.toSeq.flatMap(header => header._2.map(header._1 -> _))

    RawResponse(
      status = wsResponse.status,
      allHeaders = headers,
      body = parsePayload(wsResponse.body),
      rawData = wsResponse.body)
  }

  def executeAync(configuration: Configuration): Future[RawResponse[Response]] = {
    val wsResponse = ws.url(configuration.url)
      .withBody(configuration.payload)
      .addHttpHeaders(configuration.headers: _*)
      .addQueryStringParameters(configuration.queryStringParameters: _*)
      .withRequestTimeout(configuration.timeout)
      .withMethod(configuration.method.value).execute()

    wsResponse map responseParser map (response => {
      log(configuration, response)
      response
    })
  }

  def executeAndWait(configuration: Configuration): Try[RawResponse[Response]] = Try {
    Await.result(
      executeAync(configuration),
      Duration.Inf)
  }

  private def log(configuration: Configuration, response: RawResponse[Response]): Unit =
    if (configuration.isLogResult)
      Logger.info(APICommon.httpInfo(
        configuration.method.value,
        configuration.url,
        toString(configuration.payload),
        toString(configuration.queryStringParameters),
        response.rawData))

  protected def toString(payload: Option[T]): String = payload.map(_.toString).getOrElse("")
  private def toString(queryStringParameters: Seq[(String, String)]): String =
    queryStringParameters.foldLeft("") { (param, query) => param + s" ${query._1}=${query._2}," }
}

trait APIBaseJsValue[C <: APIConfigurationBase[JsValue]] extends APIBase[JsValue, C, JsValue] {

  override implicit val writeableT: BodyWritable[Option[JsValue]] =
    BodyWritable(payload => {
      InMemoryBody(ByteString.fromString(toString(payload)))
    }, "application/json")

  override def parsePayload(payload: String): Try[JsValue] = Try(Json.parse(payload))
}

/**
 * This uses play-json to make and get response.
 * Convert json is manual by overriding jsonFormat for request and response
 * @tparam M: Model Request Type is Case Class
 * @tparam C: Configuration
 * @tparam R: Model Reponse Type is Case Class
 */
trait APIManualParser[M, C <: APIConfigurationBase[M], R] extends APIBase[M, C, R] {

  type Model = M
  implicit val responseFormat: Format[Response]
  implicit val requestFormat: Format[Model]

  override protected def toString(payload: Option[Model]): String = payload.map(Json.toJson(_).toString).getOrElse("")

  override protected def parsePayload(payload: String): Try[Response] = Try {
    try {
      Json.parse(payload).as[Response]
    } catch {
      case _: Exception => throw new JsonParserException(s"Parse json manually error with: ${payload}")
    }
  }

  override implicit val writeableT: BodyWritable[Option[Model]] =
    BodyWritable(payload => {
      InMemoryBody(ByteString.fromString(toString(payload)))
    }, "application/json")
}

/**
 * This uses json4s to make and get response
 * Need override function: 'parsePayload' to parse Response as String to Model Response
 * @tparam M: Model Request Type is Case Class
 * @tparam C: Configuration
 * @tparam R: Model Reponse Type is Case Class
 */
trait APIAutoParser[M, C <: APIConfigurationBase[M], R] extends APIBase[M, C, R] {

  type Model = M
  implicit val formats = DefaultFormats

  override protected def toString(payload: Option[Model]): String =
    payload.map(data => pretty(render(Extraction.decompose(data)))).getOrElse("")

  override implicit val writeableT: BodyWritable[Option[Model]] =
    BodyWritable(payload => {
      InMemoryBody(ByteString.fromString(toString(payload)))
    }, "application/json")
}
