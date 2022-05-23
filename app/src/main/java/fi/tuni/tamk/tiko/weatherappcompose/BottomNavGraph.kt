package fi.tuni.tamk.tiko.weatherappcompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

//Adding destinations to Bottom Navigation items declared in BottomAppBar
@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomAppBar.Home.route
    ) {
        composable(BottomAppBar.Home.route) {
            ForecastLayout()
        }
        composable(BottomAppBar.Hourly.route) {
            Hourly()
        }
    }
}