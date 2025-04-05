package sorivma.geogeodataservice.persistence.types

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.postgis.Geometry
import org.postgis.PGgeometry
import org.postgresql.util.PGobject

class GeometryColumnType : ColumnType<Geometry>() {
    override fun sqlType(): String = "GEOMETRY"

    override fun valueFromDB(value: Any): Geometry? = when (value) {
        is PGgeometry -> value.geometry
        is PGobject -> PGgeometry.geomFromString(value.value)
        else -> null
    }

    companion object {
        fun Table.geometries(name: String): Column<Geometry> = registerColumn(name, GeometryColumnType())
    }
}

