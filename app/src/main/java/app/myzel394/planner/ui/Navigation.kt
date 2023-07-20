package app.myzel394.planner.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.myzel394.planner.database.AppDatabase
import app.myzel394.planner.models.EventsModel
import app.myzel394.planner.ui.screens.SaveEventScreen
import app.myzel394.planner.ui.screens.OverviewScreen
import kotlinx.datetime.LocalDateTime

@Composable
fun Navigation(
    database: AppDatabase,
) {
    val navController = rememberNavController()
    val eventsModel: EventsModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Overview.route) {
        composable(Screen.Overview.route) {
            OverviewScreen(
                navController = navController,
                eventsModel = eventsModel,
            )
        }
        composable(
            route = Screen.CreateEvent.route + "/{date}",
            arguments = listOf(
                navArgument("date") {
                    type = NavType.StringType
                }
            )

        ) { entry ->
            SaveEventScreen(
                navController = navController,
                date = LocalDateTime.parse(entry.arguments!!.getString("date")!!).date,
                eventsModel = eventsModel,
            )
        }
    }
}
