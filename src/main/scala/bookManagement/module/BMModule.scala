package bookManagement.module

import bookManagement.domain.internal.book.BookRepository
import bookManagement.domain.internal.borrowingHistory.BorrowingHistoryRepository
import bookManagement.domain.internal.category.CategoryRepository
import bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.book.BookRepositoryOnJDBCImpl
import bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.borrowingHistory.BorrowingHistoryRepositoryOnJDBCImpl
import bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.category.CategoryRepositoryOnJDBCImpl
import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

class BMModule extends AbstractModule with ScalaModule {
  lazy val bookRepository: BookRepository = new BookRepositoryOnJDBCImpl
  lazy val categoryRepository: CategoryRepository = new CategoryRepositoryOnJDBCImpl
  lazy val borrowingHistoryRepository: BorrowingHistoryRepository = new BorrowingHistoryRepositoryOnJDBCImpl

  override def configure(): Unit = {
    bind(classOf[BookRepository]).
      toInstance(bookRepository)
    bind(classOf[CategoryRepository]).
      toInstance(categoryRepository)
    bind(classOf[BorrowingHistoryRepository]).
      toInstance(borrowingHistoryRepository)
  }
}
