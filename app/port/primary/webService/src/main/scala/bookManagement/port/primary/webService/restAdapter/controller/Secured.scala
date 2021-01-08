package bookManagement.port.primary.webService.restAdapter.controller

import play.api.mvc._

import scala.util.{ Success, Try }

trait Secured extends BaseController {

  private def authenticateUser(requestHeader: RequestHeader): Try[String] = Success("OK")

  def withAuthenticated(f: => String => Request[AnyContent] => Result): Action[AnyContent] = Action {
    implicit request => f(authenticateUser(request).get)(request)
  }

}
