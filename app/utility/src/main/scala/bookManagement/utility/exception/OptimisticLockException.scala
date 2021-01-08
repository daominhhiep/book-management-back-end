package bookManagement.utility.exception

class OptimisticLockException(message: String, cause: Option[Throwable] = None)
  extends BaseException(message, cause)

