package bookManagement.domain.internal.requestOrder.model

import bookManagement.domain.internal.book.model.BookId
import bookManagement.domain.internal.user.model.UserId

case class RequestOrderImpl(
  identifier: RequestId,
  bookId: BookId,
  userId: UserId) extends RequestOrder
