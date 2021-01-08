package bookManagement.domain.internal.book

import bookManagement.domain.internal.book.model.{Book, BookFields, BookId, Status}
import bookManagement.utility.repository.{FeatureWithIdFieldRepository, IOContext}

import scala.util.Try

trait BookRepository extends FeatureWithIdFieldRepository[BookId, BookFields, Book] {

  def resolveAllActive(): Try[Seq[Book]]

  def findById(id: Int): Try[Book]

  def bulkCreateOrUpdate(fields: Seq[BookFields]): Try[Int]

  def updateBook(book: Book): Try[Int]

  def updateStatusBook(id: Int,status: Status): Try[Int]

  def deleteBook(id: Int): Try[Int]
}

