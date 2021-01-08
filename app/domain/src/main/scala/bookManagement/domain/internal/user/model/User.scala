package bookManagement.domain.internal.user.model

import bookManagement.utility.model.Entity

trait User extends Entity[UserId] with UserFields

object User {
  def apply(
    identifier: UserId,
    role: UserRole,
    username: String,
    email: String,
    slackName: String,
    password: String): User = UserImpl(
    identifier,
    role,
    username,
    email,
    slackName,
    password)
}

