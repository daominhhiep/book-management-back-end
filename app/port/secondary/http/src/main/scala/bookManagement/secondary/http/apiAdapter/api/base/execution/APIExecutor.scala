package bookManagement.secondary.http.apiAdapter.api.base.execution

import bookManagement.secondary.http.apiAdapter.api.base.{ APIBaseJsValue, APIConfigurationBaseJsValue, ExecutionContextForExternalApi => ExecutionContextEx }
import javax.inject.{ Inject, Singleton }
import play.api.libs.ws.WSClient

@Singleton
class APIExecutor @Inject() (override val ws: WSClient, override implicit val ec: ExecutionContextEx) extends APIBaseJsValue[APIConfigurationBaseJsValue]
