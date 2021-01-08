package bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.category

import bookManagement.domain.internal.category.CategoryRepository
import bookManagement.domain.internal.category.model._
import bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.BasicWithIdFeatureRepositoryOnJDBC

import scala.util.Try

class CategoryRepositoryOnJDBCImpl(override protected[this] val dao: CategoryDao = new CategoryDao)
  extends CategoryRepository with BasicWithIdFeatureRepositoryOnJDBC[CategoryId, CategoryFields, Category, Int, CategoryRecord] {

  override type DAO = CategoryDao

  override protected def convertToEntity(record: CategoryRecord): Category = {
    Category(
      id = CategoryId(record.categoryId),
      name = record.name,
    )
  }

  override protected def fieldsFromNamedValues(fields: CategoryFields): Seq[(Symbol, Any)] = Seq(
    'name -> fields.name
  )

  override def convertToIdentifier(id: Int): CategoryId = CategoryId(id)

  override protected def convertToRecordId(id: CategoryId): Int = id.value

  protected def toValues(fields: CategoryFields): Seq[Any] = fieldsFromNamedValues(fields).map(v => v._2)

  override def bulkCreateOrUpdate(fields: Seq[CategoryFields]): Try[Int] = {
    withDBSession { implicit session =>
      dao.bulkCreateOrUpdate(
        Seq(
          dao.column.name
        ),
        fields,
        Seq(
          dao.column.name
        ),
        toValues
      )
    }
  }
}
