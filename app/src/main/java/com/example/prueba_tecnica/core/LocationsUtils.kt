package com.example.prueba_tecnica.core

import com.google.android.gms.maps.model.LatLng
import kotlin.random.Random

object LocationsUtils {
    fun simulatedLocationFor(id: Int): LatLng {
        val baseLat = 19.4326   // CDMX
        val baseLng = -99.1332
        val r = Random(id)      // determinístico por id
        val lat = baseLat + r.nextDouble(-0.08, 0.08) // ~ ±8-9km
        val lng = baseLng + r.nextDouble(-0.08, 0.08)
        return LatLng(lat, lng)
    }
}