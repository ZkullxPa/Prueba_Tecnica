package com.example.prueba_tecnica.presentation.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.prueba_tecnica.R
import com.example.prueba_tecnica.data.model.Character
import com.example.prueba_tecnica.presentation.biometric.BiometricAccess
import com.example.prueba_tecnica.presentation.characters.ItemCard

@Composable
fun FavouriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
    navController: NavController
) {
    BiometricAccess(
        title = "Favoritos",
        subtitle = "Confirma tu identidad para continuar",
        allowDeviceCredential = true
    ) {
        val favorites by viewModel.favorites.observeAsState(emptyList())
        val listState = rememberLazyListState()

        LazyColumn(
            state = listState,
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 12.dp)
        ) {
            items(items = favorites, key = { it.characterId }) { fav ->
                FavoriteCard(fav.name, fav.image, fav.characterId, navController)
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}


@Composable
fun FavoriteCard(name: String, image: String, characterId: Int, navController: NavController){
    var isInformationVisible by remember { mutableStateOf(false) }
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        onClick = {
            navController.navigate(
                "detailCharacter/${characterId}"
            )
        },
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f).padding(start = 5.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = image,
                        contentDescription = "Character image",
                        modifier = Modifier
                            .size(100.dp)
                            .weight(1f),
                        contentScale = ContentScale.Inside
                    )
                    Column(
                        modifier = Modifier.weight(2f).fillMaxWidth().padding(start = 15.dp),
                    ) {
                        Text(
                            text = "${name}",
                            fontWeight = FontWeight(600),
                            fontSize = 16.sp,
                            modifier = Modifier
                        )
                    }
                }
            }
            IconButton(onClick = {
                isInformationVisible = true
            }){
                Icon(
                    modifier = Modifier.size(25.dp).weight(2f),
                    imageVector = Icons.Default.Info,
                    contentDescription = ""
                )
            }
        }
    }
}