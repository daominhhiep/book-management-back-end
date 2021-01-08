package bookManagement.application.feature.service.category

import bookManagement.domain.internal.category.CategoryRepository
import bookManagement.domain.internal.category.model.Category
import javax.inject.{Inject, Singleton}

import scala.util.Try

@Singleton
class CategoryService @Inject() (categoryRepository: CategoryRepository) {

  def getAll() : Try[Seq[Category]] = categoryRepository.resolveAll()
}
