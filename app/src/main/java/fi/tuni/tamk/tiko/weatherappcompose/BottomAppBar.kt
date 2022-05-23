package fi.tuni.tamk.tiko.weatherappcompose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

//All the data and options for what to display in Bottom Navigation
sealed class BottomAppBar(val route : String, val title : String, val icon : ImageVector) {
    object Home: BottomAppBar(route = "home", title = "Home", icon = Icons.Default.Home)
    object Hourly: BottomAppBar(route = "hourly", title = "Info", icon = Icons.Default.Info)
}



