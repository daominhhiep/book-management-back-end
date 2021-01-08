package bookManagement.secondary.http.apiAdapter.api.base

import scala.util.Try

case class SuccessResponse[T](data: T)

case class RawResponse[T](
  status: Int,
  allHeaders: Seq[(String, String)],
  body: Try[T],
  rawData: String)
