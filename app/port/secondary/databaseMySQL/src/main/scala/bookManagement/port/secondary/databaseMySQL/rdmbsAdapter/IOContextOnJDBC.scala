package bookManagement.port.secondary.databaseMySQL.rdmbsAdapter

import bookManagement.utility.repository.IOContext
import scalikejdbc.DBSession

case class IOContextOnJDBC(session: DBSession) extends IOContext
