package bookManagement.secondary.http.apiAdapter.api.base.config

import bookManagement.secondary.http.apiAdapter.api.base._
import play.api.libs.json.JsValue

class PostAPIDefaultConfig(override val url: String, data: JsValue) extends APIConfigurationBaseJsValue {
  override val method: Method = Method.Post
  override val payload = Some(data)
}
