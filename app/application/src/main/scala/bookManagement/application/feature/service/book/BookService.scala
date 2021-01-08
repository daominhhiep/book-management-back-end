package bookManagement.application.feature.service.book

import bookManagement.domain.internal.book.BookRepository
import bookManagement.domain.internal.book.model.{Book, Status}
import javax.inject.{Inject, Singleton}

import scala.util.Try

@Singleton
class BookService @Inject() (bookRepository: BookRepository) {

  def getAll(): Try[Seq[Book]]  = bookRepository.resolveAllActive()

  def findById(id: Int): Try[Book]  = bookRepository.findById(id)

  def createBook(book: Book) : Try[Int] = bookRepository.bulkCreateOrUpdate(Seq(book))

  def updateBook(book: Book) : Try[Int] = bookRepository.updateBook(book)

  def updateStatusBook(id: Int, status: Status) : Try[Int] = bookRepository.updateStatusBook(id, status)

  def deleteBook(id: Int) : Try[Int] = bookRepository.deleteBook(id)
}
