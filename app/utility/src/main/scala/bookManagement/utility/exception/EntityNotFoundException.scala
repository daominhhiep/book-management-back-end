package bookManagement.utility.exception

class EntityNotFoundException(message: String, cause: Option[Throwable] = None)
  extends BaseException(message, cause)
