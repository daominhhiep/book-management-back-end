package bookManagement.port.secondary.databaseMySQL.rdmbsAdapter.borrowingHistory

import bookManagement.utility.model.Record
import org.joda.time.DateTime

case class BorrowingHistoryRecord(
                                   borrowingId: Int,
                                   userId: Int,
                                   bookId: Int,
                                   startDate: DateTime,
                                   endDate: DateTime,
                                   returnDate: Option[DateTime],
                                   overdueReason: Option[String],
                                   isActive: Boolean,
                                   override val createdAt: Option[DateTime],
                                   override val updatedAt: Option[DateTime],
                                 ) extends Record