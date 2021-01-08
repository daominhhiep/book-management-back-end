package bookManagement.utility.repository

import bookManagement.utility.model

import scala.util.Try

trait Repository[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier]]

trait ResolveFeatureRepository[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier]]
  extends Repository[Identifier, Fields, Entity] {
  def resolveBy(id: Identifier)(implicit ctx: IOContext = IOContext): Try[Entity]
}

trait ResolveAllFeatureRepository[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier]]
  extends Repository[Identifier, Fields, Entity] {
  def resolveAll()(implicit ctx: IOContext = IOContext): Try[Seq[Entity]]
}

trait StoreWithIdFeatureRepository[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier]]
  extends Repository[Identifier, Fields, Entity] {
  def store(fields: Fields)(implicit ctx: IOContext = IOContext): Try[Identifier]
}

trait StoreNoIdFeatureRepository[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier]]
  extends Repository[Identifier, Fields, Entity] {
  def store(fields: Fields)(implicit ctx: IOContext = IOContext): Try[Unit]
}

trait UpdateFeatureRepository[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier]]
  extends Repository[Identifier, Fields, Entity] {
  def update(entity: Entity)(implicit ctx: IOContext = IOContext): Try[Unit]
}

trait DeleteFeatureRepository[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier]]
  extends Repository[Identifier, Fields, Entity] {
  def deleteBy(id: Identifier)(implicit ctx: IOContext = IOContext): Try[Unit]
}

trait BasicFeatureRepository[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier]]
  extends Repository[Identifier, Fields, Entity]
  with ResolveAllFeatureRepository[Identifier, Fields, Entity]

trait FeatureWithIdFieldRepository[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier]]
  extends BasicFeatureRepository[Identifier, Fields, Entity]
  with ResolveFeatureRepository[Identifier, Fields, Entity]
  with StoreWithIdFeatureRepository[Identifier, Fields, Entity]
  with UpdateFeatureRepository[Identifier, Fields, Entity]
  with DeleteFeatureRepository[Identifier, Fields, Entity]

trait FeatureNoIdFieldRepository[Identifier <: model.Identifier[_], Fields <: model.Fields, Entity <: model.Entity[Identifier]]
  extends BasicFeatureRepository[Identifier, Fields, Entity]
  with StoreNoIdFeatureRepository[Identifier, Fields, Entity]

