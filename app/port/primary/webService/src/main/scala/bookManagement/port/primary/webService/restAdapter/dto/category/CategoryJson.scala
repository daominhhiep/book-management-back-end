package bookManagement.port.primary.webService.restAdapter.dto.category

import bookManagement.domain.internal.category.model.Category

case class CategoryJson(
  id: Option[Int],
  name: String)

object CategoryResponse {

  def apply(categories: Seq[Category]): Seq[CategoryJson] = categories.map(CategoryResponse(_))

  def apply(category: Category): CategoryJson = CategoryJson(
    id = Some(category.identifier.value),
    name = category.name)
}
