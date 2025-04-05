package sorivma.geogeodataservice.persistence.types

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.postgis.PGgeometry

class GeometryColumnType : ColumnType<PGgeometry>() {
    override fun sqlType(): String = "GEOMETRY"

    override fun valueFromDB(value: Any): PGgeometry = when (value) {
        is PGgeometry -> value
        else -> error("$value cannot be converted to GEOMETRY")
    }

    companion object {
        fun Table.geometries(name: String): Column<PGgeometry> = registerColumn(name, GeometryColumnType())
    }
}

