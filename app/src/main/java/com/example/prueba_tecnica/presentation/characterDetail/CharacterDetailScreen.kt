package com.example.prueba_tecnica.presentation.characterDetail

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.prueba_tecnica.core.LocationUtils
import com.example.prueba_tecnica.data.model.Character
import com.example.prueba_tecnica.data.model.EpisodeDetail
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(characterId: Int, viewModel: CharacterDetailViewModel = hiltViewModel(), navController: NavController) {
    var isMapVisible by remember { mutableStateOf(false) }
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            Icon( //Icono para regresar a la pantalla anterior
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Cancelar",
                                modifier = Modifier
                                    .clickable {
                                        navController.popBackStack()
                                    }
                                    .size(35.dp),
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(end = 10.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Row(
                            ) {
                                Icon( //Icono para marcar como favorito
                                    imageVector = Icons.Default.Map,
                                    contentDescription = "Ubicacion",
                                    modifier = Modifier
                                        .clickable {
                                            isMapVisible = true
                                        }
                                        .size(35.dp),
                                )
                                Spacer(modifier = Modifier.padding(end = 5.dp))
                                Icon( //Icono para marcar como favorito
                                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorito",
                                    modifier = Modifier
                                        .clickable {
                                            viewModel.toggleFavorite()
                                        }
                                        .size(35.dp),
                                    tint = if (isFavorite) Color.Red else Color.Gray
                                )
                            }
                        }
                    }
                }
            )
        }
    ){innerPadding ->
        CharacterDetailBody(modifier = Modifier.padding(innerPadding), viewModel = viewModel)
    }
    if (isMapVisible) {
        MinimalDialogMaps(onDismissRequest = { isMapVisible = false }, characterId)
    }
}

@Composable
fun CharacterDetailBody(modifier: Modifier = Modifier, viewModel: CharacterDetailViewModel = hiltViewModel()){
    val episodes by viewModel.episodes.collectAsState()
    val state by viewModel.uiState.collectAsState()
    when (val s = state) {
        is CharacterDetailState.Loading -> {
            CircularProgressIndicator()
        }
        is CharacterDetailState.Error -> {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(s.message)
                Button(onClick = { viewModel.retry() }) { Text("Reintentar") }
            }
        }
        is CharacterDetailState.Success -> {
            val character = s.character
            // Pinta el detalle como quieras
            Column(
                modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f).padding(start = 10.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        AsyncImage(
                            model = character.image,
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.weight(2f),
                    ) {
                        Text(
                            text = "Nombre: ${character.name}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight(700),
                            modifier = Modifier
                        )
                        Text(
                            text = "Especie: ${character.species}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight(700),
                            modifier = Modifier
                        )
                        Text(
                            text = "Estado: ${character.status}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight(700),
                            modifier = Modifier
                        )
                        Text(
                            text = "Genero: ${character.gender}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight(700),
                            modifier = Modifier
                        )
                    }
                }
                if (character.type.isNotBlank()) Text("Type: ${character.type}")
                Text(
                    text = "Origen: ${character.characterOrigin.name}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight(700),
                    modifier = Modifier
                )
                Text(
                    text = "Localizacion: ${character.location.name}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight(700),
                    modifier = Modifier
                )
                Text("Episodios:")
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(items = episodes, key = { it.id }) {ep ->
                        EpisodeCard(
                            episode = ep
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun EpisodeCard(episode: EpisodeDetail, viewModel: CharacterDetailViewModel = hiltViewModel()){
    var favoriteEpisode by remember { mutableStateOf(false) }
    val watched by viewModel.watched.collectAsState()
    val isWatched = watched.contains(episode.id)
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 10.dp),
            ) {
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Text(
                    text = "${episode.name}",
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp
                )
                Text(
                    text = "${episode.episode}",
                    fontWeight = FontWeight(600),
                    fontSize = 16.sp
                )
                Text(
                    text = "${episode.airDate}",
                    fontWeight = FontWeight(600),
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.padding(top = 10.dp))
            }
            IconButton(onClick = {
                favoriteEpisode = true
            }){
                Icon(
                    imageVector = if (isWatched) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                    contentDescription = "Visto",
                    tint = if (isWatched) Color(0xFF2E7D32) else Color.Gray,
                    modifier = Modifier
                        .size(22.dp)
                        .clickable { viewModel.toggleEpisodeWatched(episode.id) }
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinimalDialogMaps(onDismissRequest: () -> Unit, characterId: Int) {
    val simulatedLocation = LocationUtils.getSimulatedLocation(characterId)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(simulatedLocation, 14f)
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(15.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    cameraPositionState = cameraPositionState
                ) {
                    Marker(
                        state = MarkerState(position = simulatedLocation),
                        title = "CDMX",
                        snippet = "Ubicación simulada"
                    )
                }
                Spacer(modifier = Modifier.padding(4.dp))
                Button(onClick = { onDismissRequest() }) {
                    Text("Cerrar mapa")
                }
            }
        }
    }
}
