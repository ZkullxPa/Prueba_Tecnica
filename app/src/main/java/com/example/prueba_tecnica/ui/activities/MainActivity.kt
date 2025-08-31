package com.example.prueba_tecnica.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.prueba_tecnica.presentation.characterDetail.CharacterDetailScreen
import com.example.prueba_tecnica.presentation.characterDetail.CharacterDetailViewModel
import com.example.prueba_tecnica.ui.theme.Prueba_tecnicaTheme
import com.example.prueba_tecnica.presentation.login.LoginScreen
import com.example.prueba_tecnica.presentation.login.UserViewModel
import com.example.prueba_tecnica.presentation.menu.MenuScreen
import com.example.prueba_tecnica.presentation.register.RegisterScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prueba_tecnicaTheme {
                navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize())
                { innerPadding ->
                    NavHost(navController = navController, startDestination = "login"){
                        composable("login"){
                            val viewModel = hiltViewModel<UserViewModel>()
                            LoginScreen(modifier = Modifier.padding(innerPadding), navController, viewModel)
                        }
                        composable("menu") { //Margen para el bottomNav
                            MenuScreen(modifier = Modifier.consumeWindowInsets(innerPadding), navController)
                        }
                        composable(
                            route = "detailCharacter/{id}",
                            arguments = listOf(
                                navArgument("id") { type = NavType.IntType },
                            )
                        ) { backStackEntry ->
                            val viewmodel = hiltViewModel<CharacterDetailViewModel>()
                            val id = backStackEntry.arguments?.getInt("id")
                            CharacterDetailScreen(
                                characterId = id ?: 0,
                                viewmodel,
                                navController
                            )
                        }
                        composable("register"){
                            val viewModel = hiltViewModel<UserViewModel>()
                            RegisterScreen(modifier = Modifier.padding(innerPadding), navController, viewModel)
                        }
                    }
                }
            }
        }
    }
}
