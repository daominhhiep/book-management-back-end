package bookManagement.port.primary.webService.restAdapter.controller.category

import bookManagement.application.feature.service.category.CategoryService
import bookManagement.port.primary.webService.restAdapter.controller.APIController
import bookManagement.domain.internal.category.CategoryRepository
import bookManagement.port.primary.webService.restAdapter.dto.category.CategoryResponse

import javax.inject.Inject
import play.api.mvc._

class CategoryController @Inject() (cc: ControllerComponents, categoryService: CategoryService, categoryRepository: CategoryRepository) extends APIController(cc) {

  def list = Action {
    success(CategoryResponse(categoryService.getAll().get))
  }
}
