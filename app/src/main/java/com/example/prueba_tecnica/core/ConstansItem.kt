package com.example.prueba_tecnica.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Person
import com.example.prueba_tecnica.data.model.NavBarItem

object ConstansItem {
    val NavBarItems = listOf(
        NavBarItem(
            icon = Icons.Default.Person,
            route = "characters"
        ),
        NavBarItem(
            icon = Icons.Default.Favorite,
            route = "favourite"
        ),
        NavBarItem(
            icon = Icons.Default.Navigation,
            route = "map"
        )
    )
}