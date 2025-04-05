package sorivma.geogeodataservice.persistence.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.timestamp

object Layers : UUIDTable("layers") {
    val name = text("name")
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")
    val createdBy = text("created_by").nullable()
    val projectId = uuid("project_id").nullable()
    val geometryType = text("geometry_type").nullable()
    val srid = integer("srid").default(4326)
    val isPublic = bool("is_public").default(false)
}
