package bookManagement.domain.internal.requestOrder.model

import bookManagement.domain.internal.book.model.BookId
import bookManagement.domain.internal.user.model.UserId
import bookManagement.utility.model.Entity

trait RequestOrder extends Entity[RequestId] with RequestOrderFields

object RequestOrder {
  def apply(
    identifier: RequestId,
    bookId: BookId,
    userId: UserId): RequestOrder = RequestOrderImpl(
    identifier,
    bookId,
    userId)
}
