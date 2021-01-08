package bookManagement.domain.internal.user.model

case class UserImpl(
  identifier: UserId,
  role: UserRole,
  username: String,
  email: String,
  slackName: String,
  password: String) extends User
