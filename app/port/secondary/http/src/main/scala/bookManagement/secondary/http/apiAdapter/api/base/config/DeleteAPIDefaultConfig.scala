package bookManagement.secondary.http.apiAdapter.api.base.config

import bookManagement.secondary.http.apiAdapter.api.base._

class DeleteAPIDefaultConfig(override val url: String) extends APIConfigurationBaseJsValue {
  override val method: Method = Method.Delete
}
