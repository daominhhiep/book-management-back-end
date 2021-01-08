package bookManagement.domain.internal.book.model

import bookManagement.utility.model.Identifier

case class BookId(value: Int) extends Identifier[Int]
