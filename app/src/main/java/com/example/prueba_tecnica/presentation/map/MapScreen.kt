package com.example.prueba_tecnica.presentation.map

import androidx.compose.foundation.gestures.snapping.SnapPosition.Center.position
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.prueba_tecnica.data.model.Character
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prueba_tecnica.core.LocationsUtils.simulatedLocationFor
import com.example.prueba_tecnica.presentation.characters.CharactersState
import com.example.prueba_tecnica.presentation.characters.CharactersViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.*
import com.google.android.gms.maps.model.*
import kotlin.collections.map

@Composable
fun MapRoute(
    modifier: Modifier = Modifier,
    viewModel: CharactersViewModel = hiltViewModel()
) {
    val state by viewModel.charactersState.collectAsState()

    when (val s = state) {
        is CharactersState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        is CharactersState.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Error: ${s.message}") }
        is CharactersState.Success -> {
            MapScreen(
                modifier = modifier,
                characters = s.data.results
            )
        }
    }
}
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    characters: List<Character>
) {
    if (characters.isEmpty()) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Sin personajes") }
        return
    }
    val points = remember(characters) {
        characters
            .distinctBy { it.id }
            .map { it.id to simulatedLocationFor(it.id) }
    }
    val first = points.first().second
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(first, 10f)
    }

    LaunchedEffect(points) {
        if (points.size > 1) {
            val builder = LatLngBounds.Builder()
            points.forEach { builder.include(it.second) }
            val bounds = builder.build()
            val paddingPx = 80
            cameraPositionState.animate(CameraUpdateFactory.newLatLngBounds(bounds, paddingPx))
        }
    }
    Box(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            points.forEach { (id, latLng) ->
                val name = characters.firstOrNull { it.id == id }?.name ?: "Personaje $id"
                Marker(
                    state = MarkerState(position = latLng),
                    title = name,
                )
            }
        }
    }
}
