package bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.book

import bookManagement.domain.internal.book.model.{Book, BookId, Status}
import bookManagement.domain.internal.category.model.CategoryId
import bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.WithTestTable
import org.joda.time.DateTime
import org.junit.runner.RunWith
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import scalikejdbc.specs2.mutable.AutoRollback

@RunWith(classOf[JUnitRunner])
class BookRepositoryOnJDBCImplSpec extends Specification with WithTestTable{

  override val testTableName = "book"

  private val repository = new BookRepositoryOnJDBCImpl

  private val currentDate = new DateTime()

  private[this] val book = Book(
    BookId(1),
    "HarryPotter",
    "JK",
    CategoryId(1),
    currentDate,
    Some("Description"),
    Status.Ready,
    Some(currentDate),
    Some(currentDate)
  )

  sequential

  "Store successfully" in new AutoRollback {
    withSession {
      repository.bulkCreateOrUpdate(Seq(book)).isSuccess must beEqualTo(true)
    }
  }

  "ResolveAll Successfully" in new AutoRollback {
    withSession {
      repository.bulkCreateOrUpdate(Seq(book))

      val result = repository.resolveAll()
      result.get.head.name must beEqualTo("HarryPotter")
    }
  }
}
