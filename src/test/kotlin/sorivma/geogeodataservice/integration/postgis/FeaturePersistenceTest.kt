package sorivma.geogeodataservice.integration.postgis

import junit.framework.TestCase.assertNotNull
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.postgis.PGgeometry
import org.postgis.Point
import sorivma.geogeodataservice.persistence.table.FeatureVersions
import sorivma.geogeodataservice.persistence.table.Features
import sorivma.geogeodataservice.persistence.table.Layers
import java.time.Instant
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class FeaturePersistenceTest : AbstractPostgisIntegrationTest() {
    @Test
    fun `should save Feature and link to FeatureVersion`() {
        transaction {
            val now = Instant.now()

            val layerId = Layers.insertAndGetId {
                it[name] = "Layer"
                it[createdAt] = now
                it[updatedAt] = now
                it[createdBy] = "Author"
                it[projectId] = UUID.randomUUID()
                it[geometryType] = "Point"
                it[srid] = 4326
                it[isPublic] = true
            }

            val featureId = Features.insertAndGetId {
                it[layer] = layerId
                it[createdAt] = now
                it[updatedAt] = now
            }

            val geometry = PGgeometry(Point(1.0, 2.0).apply { srid = 4326 })

            val versionId = FeatureVersions.insertAndGetId {
                it[feature] = featureId
                it[FeatureVersions.geometry] = geometry
                it[createdBy] = "Geo Author"
                it[versionNumber] = 1
                it[createdAt] = now
                it[isDeleted] = false
            }

            Features.update({ Features.id eq featureId }) {
                it[currentVersion] = versionId
            }

            val result = Features.selectAll().firstOrNull { it[Features.id] == featureId }!!

            assertNotNull(result)
            assertEquals(versionId, result[Features.currentVersion])
        }
    }
}