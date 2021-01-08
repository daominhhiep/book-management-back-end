package bookManagement.port.secondary.databaseMySQL.rdmbsAdapter

import org.specs2.mutable.Specification
import scalikejdbc._
import scalikejdbc.config.DBs

trait WithTestTable extends Specification {

  sequential

  implicit val session: DBSession = AutoSession

  DBs.setupAll()

  protected val testTableName: String

  def deleteAllTestRecord(): Unit = {
    DB autoCommit { implicit session =>
      SQL(
        s"DELETE FROM `$testTableName`"
      ).execute().apply()
      ()
    }
  }

  def withSession[T](f: => T): T = {
    try {
      f
    } finally {
      deleteAllTestRecord()
    }
  }

}
