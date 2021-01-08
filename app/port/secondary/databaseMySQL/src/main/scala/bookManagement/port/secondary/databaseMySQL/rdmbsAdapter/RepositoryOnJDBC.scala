package bookManagement.port.secondary.databaseMySQL.rdmbsAdapter

import bookManagement.utility.exception.{EntityNotFoundException, OptimisticLockException}
import bookManagement.utility.model
import bookManagement.utility.repository._
import scalikejdbc._
import scalikejdbc.jodatime.JodaBinders

import scala.util.{Success, Try}
trait BaseRepositoryOnJDBC[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier], Record <: model.Record] extends Repository[Identifier, Fields, Entity] {

  protected def getDBSession(implicit ctx: IOContext): Try[DBSession] = {
    ctx match {
      case IOContextOnJDBC(session) => Success(session)
      case _ =>
        Success(AutoSession)
    }
  }

  protected def withDBSession[T](func: DBSession => T)(implicit ctx: IOContext = IOContext): Try[T] = {
    getDBSession.map(func)
  }
}

trait RepositoryWithIdOnJDBC[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier], RecordId, Record <: model.Record]
  extends BaseRepositoryOnJDBC[Identifier, Fields, Entity, Record] {

  type DAO <: CRUDMapperWithId[RecordId, Fields, Record]

  protected[this] val dao: DAO

  protected def convertToRecordId(identifier: Identifier): RecordId
}

trait RepositoryNoIdOnJDBC[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier], Record <: model.Record]
  extends BaseRepositoryOnJDBC[Identifier, Fields, Entity, Record] {

  type DAO <: CRUDMapperNoId[Fields, Record]

  protected[this] val dao: DAO

}

trait ResolveWithIdFeatureRepositoryOnJDBC[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier], RecordId, Record <: model.Record]
  extends RepositoryWithIdOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with ResolveFeatureRepository[Identifier, Fields, Entity] {

  protected def convertToEntity(record: Record): Entity

  def resolveBy(identifier: Identifier)(implicit ctx: IOContext = IOContext): Try[Entity] = withDBSession { implicit session =>
    val entity = dao.findById(convertToRecordId(identifier)).map(convertToEntity)
    entity.getOrElse(throw new EntityNotFoundException(s"models = ${dao.tableName}, id = $identifier"))
  }
}

trait ResolveAllWithIdFeatureRepositoryOnJDBC[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier], RecordId, Record <: model.Record]
  extends RepositoryWithIdOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with ResolveAllFeatureRepository[Identifier, Fields, Entity] {

  protected def convertToEntity(record: Record): Entity

  def resolveAll()(implicit ctx: IOContext = IOContext): Try[Seq[Entity]] = withDBSession { implicit session =>
    dao.findAll().map(convertToEntity)
  }
}

trait ResolveAllNoIdFeatureRepositoryOnJDBC[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier], Record <: model.Record]
  extends RepositoryNoIdOnJDBC[Identifier, Fields, Entity, Record]
  with ResolveAllFeatureRepository[Identifier, Fields, Entity] {

  protected def convertToEntity(record: Record): Entity

  def resolveAll()(implicit ctx: IOContext = IOContext): Try[Seq[Entity]] = withDBSession { implicit session =>
    dao.findAll().map(convertToEntity)
  }
}

trait StoreWithIdFeatureRepositoryOnJDBC[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier], RecordId, Record <: model.Record]
  extends RepositoryWithIdOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with StoreWithIdFeatureRepository[Identifier, Fields, Entity] {

  protected def fieldsFromNamedValues(fields: Fields): Seq[(Symbol, Any)]

  def convertToIdentifier(id: RecordId): Identifier

  def store(fields: Fields)(implicit ctx: IOContext = IOContext): Try[Identifier] = withDBSession { implicit session =>
    val createAttributes = fieldsFromNamedValues(fields)
    val id = dao.createWithAttributes(createAttributes: _*)
    convertToIdentifier(id)
  }
}

trait StoreNoIdFeatureRepositoryOnJDBC[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier], Record <: model.Record]
  extends RepositoryNoIdOnJDBC[Identifier, Fields, Entity, Record] {

  protected def fieldsFromNamedValues(fields: Fields): Seq[(Symbol, Any)]

  def store(fields: Fields)(implicit ctx: IOContext = IOContext): Try[Unit] = withDBSession { implicit session =>
    val createAttributes = fieldsFromNamedValues(fields)
    val id = dao.createWithAttributes(createAttributes: _*)
    ()
  }
}

trait UpdateWithIdFeatureRepositoryOnJDBC[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier], RecordId, Record <: model.Record]
  extends RepositoryWithIdOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with UpdateFeatureRepository[Identifier, Fields, Entity] {

  protected def fieldsFromNamedValues(fields: Fields): Seq[(Symbol, Any)]

  override def update(entity: Entity)(implicit ctx: IOContext = IOContext): Try[Unit] = withDBSession { implicit session =>
    val createAttributes = fieldsFromNamedValues(entity.asInstanceOf[Fields])
    implicit val e = ParameterBinderFactory.asisParameterBinderFactory
    implicit val jodaDateTimeParameterBinderFactory: ParameterBinderFactory[org.joda.time.DateTime] = JodaBinders.jodaDateTime
    val updatedCount = dao.updateBy(sqls.eq(dao.column.id, entity.identifier.value.asInstanceOf[Any]).and(sqls.eq(dao.column.field("updatedAt"), entity.updatedAt)))
      .withAttributes(createAttributes: _*)
    if (updatedCount == 1) {
      ()
    } else {
      throw new OptimisticLockException(s"models = ${dao.tableName}, id = $entity.identifier")
    }
  }
}

trait DeleteWithIdFeatureRepositoryOnJDBC[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier], RecordId, Record <: model.Record]
  extends RepositoryWithIdOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with DeleteFeatureRepository[Identifier, Fields, Entity] {

  def deleteBy(identifier: Identifier)(implicit ctx: IOContext = IOContext): Try[Unit] = withDBSession { implicit session =>
    val deletedCount = dao.deleteById(convertToRecordId(identifier))

    if (deletedCount == 1) {
      ()
    } else {
      throw new EntityNotFoundException(s"models = ${dao.tableName}, id = $identifier")
    }
  }
}

// summary feature traits
trait BasicWithIdFeatureRepositoryOnJDBC[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier], RecordId, Record <: model.Record]
  extends RepositoryWithIdOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with ResolveWithIdFeatureRepositoryOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with ResolveAllWithIdFeatureRepositoryOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with StoreWithIdFeatureRepositoryOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with UpdateWithIdFeatureRepositoryOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with DeleteWithIdFeatureRepositoryOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with FeatureWithIdFieldRepository[Identifier, Fields, Entity]

trait BasicNoIdFeatureRepositoryOnJDBC[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier], Record <: model.Record]
  extends RepositoryNoIdOnJDBC[Identifier, Fields, Entity, Record]
  with ResolveAllNoIdFeatureRepositoryOnJDBC[Identifier, Fields, Entity, Record]
  with StoreNoIdFeatureRepositoryOnJDBC[Identifier, Fields, Entity, Record]
