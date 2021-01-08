package bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.category

import bookManagement.utility.model.Record

case class CategoryRecord(categoryId: Int, name: String) extends Record
