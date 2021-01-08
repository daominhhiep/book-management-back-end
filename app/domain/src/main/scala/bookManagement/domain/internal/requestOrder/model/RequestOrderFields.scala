package bookManagement.domain.internal.requestOrder.model

import bookManagement.domain.internal.book.model.BookId
import bookManagement.domain.internal.user.model.UserId
import bookManagement.utility.model.Fields

trait RequestOrderFields extends Fields {
  val bookId: BookId
  val userId: UserId
}
