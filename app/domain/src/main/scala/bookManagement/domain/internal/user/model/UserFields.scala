package bookManagement.domain.internal.user.model

import bookManagement.utility.model.Fields

trait UserFields extends Fields {
  val role: UserRole
  val username: String
  val email: String
  val slackName: String
  val password: String
}
