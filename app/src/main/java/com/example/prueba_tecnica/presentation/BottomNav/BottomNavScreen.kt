package com.example.prueba_tecnica.presentation.BottomNav

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prueba_tecnica.R
import com.example.prueba_tecnica.core.ConstansItem.NavBarItems

@Composable
fun BottomNavBar(navController: NavController){
    val selectedItem = rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar(modifier = Modifier.navigationBarsPadding().height(40.dp), containerColor = MaterialTheme.colorScheme.surface){
        NavBarItems.forEachIndexed { index, item ->
            NavigationBarItem(
                modifier = Modifier.navigationBarsPadding(),
                selected = selectedItem.intValue == index,
                onClick = {
                    selectedItem.intValue = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = "", modifier = Modifier.navigationBarsPadding().size(35.dp))
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(R.color.bluelight),
                    indicatorColor = Color.Transparent,
                )
            )
        }
    }
}