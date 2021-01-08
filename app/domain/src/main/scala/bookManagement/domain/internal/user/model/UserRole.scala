package bookManagement.domain.internal.user.model

sealed abstract class UserRole(val value: String)

object UserRole {
  case object Admin extends UserRole("ADMIN")
  case object Member extends UserRole("MEMBER")

  def apply(role: String): Option[UserRole] =
    role match {
      case "ADMIN" => Some(UserRole.Admin)
      case "MEMBER" => Some(UserRole.Member)
      case _ => None
    }
}
