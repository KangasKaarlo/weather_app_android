package fi.tuni.tamk.tiko.weatherappcompose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomAppBar(val route : String, val title : String, val icon : ImageVector) {
    object Home: BottomAppBar(route = "home", title = "Home", icon = Icons.Default.Home)
    object Hourly: BottomAppBar(route = "hourly", title = "Hourly", icon = Icons.Default.DateRange)
}



