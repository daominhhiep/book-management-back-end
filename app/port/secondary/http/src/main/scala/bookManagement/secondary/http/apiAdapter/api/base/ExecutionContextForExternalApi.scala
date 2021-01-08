package bookManagement.secondary.http.apiAdapter.api.base

import akka.actor.ActorSystem
import javax.inject.{ Inject, Singleton }
import play.api.libs.concurrent.CustomExecutionContext

@Singleton
class ExecutionContextForExternalApi @Inject() (system: ActorSystem)
  extends CustomExecutionContext(system, "external-api-dispatcher")
