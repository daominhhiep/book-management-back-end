package bookManagement.domain.internal.category

import bookManagement.domain.internal.category.model.{ Category, CategoryFields, CategoryId }
import bookManagement.utility.repository.FeatureWithIdFieldRepository

import scala.util.Try

trait CategoryRepository extends FeatureWithIdFieldRepository[CategoryId, CategoryFields, Category] {
  def bulkCreateOrUpdate(fields: Seq[CategoryFields]): Try[Int]
}

