package bookManagement.domain.internal.book.model

sealed abstract class Status(val value: String)

object Status {
  case object Ready extends Status("READY")
  case object Borrowing extends Status("BORROWING")
  case object Overdue extends Status("OVERDUE")

  def apply(value: String): Option[Status] =
    value match {
      case "BORROWING" => Some(Status.Borrowing)
      case "OVERDUE" => Some(Status.Overdue)
      case "READY" => Some(Status.Ready)
      case _ => None
    }
}
