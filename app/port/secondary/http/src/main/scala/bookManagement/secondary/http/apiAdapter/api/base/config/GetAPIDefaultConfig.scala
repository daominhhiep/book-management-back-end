package bookManagement.secondary.http.apiAdapter.api.base.config

import bookManagement.secondary.http.apiAdapter.api.base._

class GetAPIDefaultConfig(override val url: String) extends APIConfigurationBaseJsValue {
  override val method: Method = Method.Get
}
