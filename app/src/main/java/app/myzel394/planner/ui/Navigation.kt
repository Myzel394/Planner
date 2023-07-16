package app.myzel394.planner.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.myzel394.planner.ui.screens.CreateEventScreen
import app.myzel394.planner.ui.screens.OverviewScreen
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Overview.route) {
        composable(Screen.Overview.route) {
            OverviewScreen(navController = navController)
        }
        composable(
            route = Screen.CreateEvent.route + "/{date}",
            arguments = listOf(
                navArgument("date") {
                    type = NavType.StringType
                }
            )

        ) { entry ->
            CreateEventScreen(
                navController = navController,
                date = LocalDateTime.parse(entry.arguments!!.getString("date")!!).date,
            )
        }
    }
}
