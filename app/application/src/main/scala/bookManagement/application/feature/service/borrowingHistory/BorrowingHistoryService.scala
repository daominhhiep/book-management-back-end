package bookManagement.application.feature.service.borrowingHistory

import bookManagement.domain.internal.borrowingHistory.BorrowingHistoryRepository
import bookManagement.domain.internal.borrowingHistory.model.BorrowingHistory
import javax.inject.{Inject, Singleton}

import scala.util.Try

@Singleton
class BorrowingHistoryService @Inject()(borrowingHistoryRepo: BorrowingHistoryRepository) {

  def getAll(): Try[Seq[BorrowingHistory]] = borrowingHistoryRepo.resolveAllActive()

  def getByBookId(bookId: Int): Try[Seq[BorrowingHistory]] = borrowingHistoryRepo.resolveByBookId(bookId)

  def delete(id: Int): Try[Int] = {
    for {
      _ <- borrowingHistoryRepo.findById(id)
      status <- borrowingHistoryRepo.delete(id)
    } yield status
  }

  def create(borrowingHistory: BorrowingHistory): Try[Int] = borrowingHistoryRepo.bulkCreateOrUpdate(Seq(borrowingHistory))

  def update(borrowingHistory: BorrowingHistory): Try[Int] = {
    for {
      borrowingOld <- borrowingHistoryRepo.findById(borrowingHistory.identifier.value)
      borrowingNew = borrowingOld.update(borrowingHistory)
      status <- borrowingHistoryRepo.update(borrowingNew)
    } yield status
  }
}
