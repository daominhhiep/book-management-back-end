package bookManagement.domain.internal.category.model

case class CategoryImpl(
  identifier: CategoryId,
  name: String) extends Category
