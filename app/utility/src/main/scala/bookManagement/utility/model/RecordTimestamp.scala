package bookManagement.utility.model

import org.joda.time.DateTime

trait RecordTimestamp {
  val createdAt: Option[DateTime] = None
  val updatedAt: Option[DateTime] = None
}

