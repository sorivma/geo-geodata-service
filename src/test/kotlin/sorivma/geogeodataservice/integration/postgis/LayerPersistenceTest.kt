package sorivma.geogeodataservice.integration.postgis

import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import sorivma.geogeodataservice.persistence.table.Layers
import java.time.Instant
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class LayerPersistenceTest : AbstractPostgisIntegrationTest() {
    @Test
    fun `should save and read Layer correctly`() {
        transaction {
            val now = Instant.now()
            val testId = UUID.randomUUID()

            val id = Layers.insertAndGetId {
                it[name] = "Layer 1"
                it[createdAt] = now
                it[updatedAt] = now
                it[createdBy] = "John Doe"
                it[projectId] = testId
                it[geometryType] = "LineString"
                it[srid] = 4326
                it[isPublic] = true
            }

            val result = Layers.selectAll().firstOrNull { it[Layers.id] == id }

            assertNotNull(result)
            assertEquals("Layer 1", result[Layers.name])
            assertEquals(now.epochSecond, result[Layers.createdAt].epochSecond)
            assertEquals(now.epochSecond, result[Layers.updatedAt].epochSecond)
            assertEquals("John Doe", result[Layers.createdBy])
            assertEquals(testId, result[Layers.projectId])
            assertEquals("LineString", result[Layers.geometryType])
            assertEquals(4326, result[Layers.srid])
            assertTrue(result[Layers.isPublic])
        }
    }

    @Test
    fun `should save layer with minimal data and isPublic false`() {
        transaction {
            val now = Instant.now()

            val id = Layers.insertAndGetId {
                it[name] = "Layer 2"
                it[createdAt] = now
                it[updatedAt] = now
                it[createdBy] = null
                it[projectId] = null
                it[geometryType] = null
                it[srid] = 0
                it[isPublic] = false
            }

            val result = Layers.selectAll().firstOrNull { it[Layers.id] == id }

            assertNotNull(result)
            assertEquals("Layer 2", result[Layers.name])
            assertEquals(now.epochSecond, result[Layers.createdAt].epochSecond)
            assertEquals(now.epochSecond, result[Layers.updatedAt].epochSecond)
            assertNull(result[Layers.createdBy])
            assertNull(result[Layers.projectId])
            assertNull(result[Layers.geometryType])
            assertEquals(0, result[Layers.srid])
            assertFalse(result[Layers.isPublic])
        }
    }

    @Test
    fun `should update layer correctly`() {
        transaction {
            val id = Layers.insertAndGetId {
                it[name] = "Old Name"
                it[createdAt] = Instant.now()
                it[updatedAt] = Instant.now()
                it[createdBy] = "User A"
                it[projectId] = UUID.randomUUID()
                it[geometryType] = "Polygon"
                it[srid] = 3857
                it[isPublic] = false
            }

            val newName = "Updated Layer"
            val newTime = Instant.now()

            Layers.update({ Layers.id eq id }) {
                it[name] = newName
                it[updatedAt] = newTime
                it[isPublic] = true
            }

            val result = Layers.selectAll().firstOrNull { it[Layers.id] == id }

            assertNotNull(result)
            assertEquals(newName, result[Layers.name])
            assertEquals("User A", result[Layers.createdBy])
            assertEquals("Polygon", result[Layers.geometryType])
            assertEquals(3857, result[Layers.srid])
            assertTrue(result[Layers.isPublic])
            assertEquals(newTime.epochSecond, result[Layers.updatedAt].epochSecond)
        }
    }

    @Test
    fun `should not allow duplicate layer name within same project`() {
        transaction {
            val testProjectId = UUID.randomUUID()
            val name = "Unique Layer"

            Layers.insert {
                it[Layers.name] = name
                it[createdAt] = Instant.now()
                it[updatedAt] = Instant.now()
                it[projectId] = testProjectId
            }

            assertFailsWith<ExposedSQLException> {
                Layers.insert {
                    it[Layers.name] = name
                    it[createdAt] = Instant.now()
                    it[updatedAt] = Instant.now()
                    it[projectId] = testProjectId
                }
            }
        }
    }
}