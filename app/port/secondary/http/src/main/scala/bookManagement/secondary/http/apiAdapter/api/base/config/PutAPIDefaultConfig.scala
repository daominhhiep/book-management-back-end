package bookManagement.secondary.http.apiAdapter.api.base.config

import bookManagement.secondary.http.apiAdapter.api.base.{ APIConfigurationBaseJsValue, Method }
import play.api.libs.json.JsValue

class PutAPIDefaultConfig(override val url: String, data: JsValue) extends APIConfigurationBaseJsValue {
  override val method: Method = Method.Delete
  override val payload = Some(data)
}
