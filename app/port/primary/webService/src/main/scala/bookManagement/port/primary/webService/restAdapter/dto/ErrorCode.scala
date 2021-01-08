package bookManagement.port.primary.webService.restAdapter.dto

sealed abstract class ErrorCode(val value: Int)

object ErrorCode {
  private val UNKNOWN_ERROR = 1
  private val ENTITY_NOT_FOUND_ERROR = 2
  private val PARAMETER_FORMAT_ERROR = 3
  private val EMAIL_CONFIG_ERROR = 4
  private val OPTIMISTIC_LOCK_EXCEPTION = 11
  private val UNAUTHORIZED = 401
  private val FORBIDDEN = 403
  private val PERMISSION_DENIED = 5
  private val INTERNAL_SERVER_ERROR = 500

  case object UnknownError extends ErrorCode(UNKNOWN_ERROR)

  case object EntityNotFoundError extends ErrorCode(ENTITY_NOT_FOUND_ERROR)

  case object ParameterFormatError extends ErrorCode(PARAMETER_FORMAT_ERROR)

  case object EmailConfigError extends ErrorCode(EMAIL_CONFIG_ERROR)

  case object OptimisticLockException extends ErrorCode(OPTIMISTIC_LOCK_EXCEPTION)

  case object Unauthorized extends ErrorCode(UNAUTHORIZED)

  case object PermissionDenied extends ErrorCode(PERMISSION_DENIED)

  case object ForbiddenError extends ErrorCode(FORBIDDEN)

  case object InternalServerError extends ErrorCode(INTERNAL_SERVER_ERROR)

}
