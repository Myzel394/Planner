package app.myzel394.planner.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.myzel394.planner.ui.screens.CreateEventScreen
import app.myzel394.planner.ui.screens.OverviewScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Overview.route) {
        composable(Screen.Overview.route) {
            OverviewScreen()
        }
        composable(Screen.CreateEvent.route) {
            CreateEventScreen()
        }
    }
}
