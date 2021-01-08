package bookManagement.domain.internal.borrowingHistory

import bookManagement.domain.internal.borrowingHistory.model.{BorrowingHistory, BorrowingHistoryFields, BorrowingId}
import bookManagement.utility.repository.FeatureWithIdFieldRepository

import scala.util.Try

trait BorrowingHistoryRepository extends FeatureWithIdFieldRepository[BorrowingId, BorrowingHistoryFields, BorrowingHistory] {
  def resolveAllActive(): Try[Seq[BorrowingHistory]]

  def resolveByBookId(bookId: Int): Try[Seq[BorrowingHistory]]

  def findById(id: Int): Try[BorrowingHistory]

  def bulkCreateOrUpdate(fields: Seq[BorrowingHistoryFields]): Try[Int]

  def update(borrowingHistory: BorrowingHistory): Try[Int]

  def delete(id: Int): Try[Int]

}
