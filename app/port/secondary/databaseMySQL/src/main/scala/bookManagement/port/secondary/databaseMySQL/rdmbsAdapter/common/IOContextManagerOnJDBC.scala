package bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.common

import bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.IOContextOnJDBC
import bookManagement.utility.repository.{IOContext, IOContextManager}
import scalikejdbc._

class IOContextManagerOnJDBC extends IOContextManager {

  private val database = 'default

  def context: IOContext = IOContextOnJDBC(AutoSession)

  def transactionalContext[T](execution: (IOContext) => T): T = {
    NamedDB(database) localTx { session =>
      execution(IOContextOnJDBC(session))
    }
  }
}
