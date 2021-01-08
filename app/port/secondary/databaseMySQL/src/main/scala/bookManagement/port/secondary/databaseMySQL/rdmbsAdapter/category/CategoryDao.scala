package bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.category

import bookManagement.domain.internal.category.model.CategoryFields
import bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.CRUDMapperWithId

import scalikejdbc._

private[category] class CategoryDao extends CRUDMapperWithId[Int, CategoryFields, CategoryRecord]{

  override lazy val tableName = "category"
  override lazy val defaultAlias = createAlias("category")
  override lazy val primaryKeyFieldName = "category_id"

  override def columnNames: Seq[String] = Seq(
    "category_id",
    "name"
  )

  override def toNamedValues(record: CategoryRecord): Seq[(Symbol, Any)] = Seq(
    'category_id -> record.categoryId,
    'name        -> record.name
  )

  override def idToRawValue(id: Int): Int = id
  override def rawValueToId(value: Any): Int = value.asInstanceOf[Int]

  override def extract(rs: WrappedResultSet, n: ResultName[CategoryRecord]): CategoryRecord = {
    CategoryRecord(
      categoryId = rs.get(n.categoryId),
      name = rs.get(n.name)
    )
  }
}
