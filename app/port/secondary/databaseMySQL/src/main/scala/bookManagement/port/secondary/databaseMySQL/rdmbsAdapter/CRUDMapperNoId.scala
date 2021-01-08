package bookManagement.port.secondary.databaseMySQL.rdmbsAdapter

import skinny.orm.SkinnyNoIdCRUDMapper

trait CRUDMapperNoId[ObjectFields, Entity] extends CRUDMapper[ObjectFields, Entity] with SkinnyNoIdCRUDMapper[Entity] {

}
