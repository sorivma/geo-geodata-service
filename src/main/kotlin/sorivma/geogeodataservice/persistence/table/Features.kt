package sorivma.geogeodataservice.persistence.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.timestamp

object Features : UUIDTable("features") {
    val layer = reference("layer_id", Layers, onDelete = ReferenceOption.CASCADE)
    val currentVersion = reference("current_version", FeatureVersions, onDelete = ReferenceOption.NO_ACTION)
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")
}