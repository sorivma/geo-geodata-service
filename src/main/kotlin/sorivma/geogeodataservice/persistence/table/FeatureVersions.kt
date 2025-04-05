package sorivma.geogeodataservice.persistence.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.timestamp
import sorivma.geogeodataservice.persistence.types.GeometryColumnType.Companion.geometries

object FeatureVersions : UUIDTable("feature_versions") {
    val feature = reference("feature_id", Features)
    val geometry = geometries("geometry")
    val createdBy = text("created_by").nullable()
    val versionNumber = integer("version_number")
    val createdAt = timestamp("created_at")
    val isDeleted = bool("is_deleted").default(false)

    init {
        uniqueIndex("uq_feature_versions_version", feature, versionNumber)
    }
}