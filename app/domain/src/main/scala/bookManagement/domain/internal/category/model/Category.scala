package bookManagement.domain.internal.category.model

import bookManagement.utility.model.Entity

trait Category extends Entity[CategoryId] with CategoryFields

object Category {
  def apply(id: CategoryId, name: String): Category = CategoryImpl(id, name)
}