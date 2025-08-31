package com.example.prueba_tecnica.core

import com.google.android.gms.maps.model.LatLng

object LocationUtils {
    fun getSimulatedLocation(characterId: Int): LatLng {
        val baseLat = 19.4326
        val baseLng = -99.1332
        val latOffset = (characterId % 100) * 0.001
        val lngOffset = (characterId % 100) * 0.001
        return LatLng(baseLat + latOffset, baseLng + lngOffset)
    }
}