package com.example.prueba_tecnica.presentation.menu

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.prueba_tecnica.presentation.characters.CharactersScreen
import com.example.prueba_tecnica.presentation.favourites.FavouriteScreen
import com.example.prueba_tecnica.presentation.BottomNav.BottomNavBar
import com.example.prueba_tecnica.presentation.characters.CharactersViewModel
import com.example.prueba_tecnica.presentation.favourites.FavoriteViewModel
import com.example.prueba_tecnica.presentation.map.MapRoute
import com.example.prueba_tecnica.presentation.map.MapScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(modifier: Modifier,navController: NavController){
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavBar(navController = bottomNavController) },
    ){innerPadding ->
        val graph = bottomNavController.createGraph(startDestination = "characters"){
            composable("characters") {
                val viewModel = hiltViewModel<CharactersViewModel>()
                CharactersScreen(modifier = Modifier.padding(innerPadding), viewModel, navController)
            }
            composable("favourite") {
                val viewModel = hiltViewModel<FavoriteViewModel>()
                FavouriteScreen(modifier = Modifier.padding(innerPadding), viewModel, navController)
            }
            composable("map") {
                val viewModel = hiltViewModel<CharactersViewModel>()
                MapRoute(modifier = Modifier.padding(innerPadding), viewModel)
            }
        }
        NavHost(
            navController = bottomNavController,
            graph = graph,
        )
    }
}