package sorivma.geogeodataservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GeoGeodataServiceApplication

fun main(args: Array<String>) {
    runApplication<GeoGeodataServiceApplication>(*args)
}