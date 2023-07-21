package app.myzel394.planner.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
            route = Screen.SaveEvent.route + "/{date}/{event}",
            arguments = listOf(
                navArgument("date") {
                    type = NavType.StringType
                },
                navArgument("event") {
                    type = NavType.LongType
                    defaultValue = 0L
                }
            )

        ) { entry ->
            val event = entry.arguments!!.getLong("event", 0L)

            SaveEventScreen(
                navController = navController,
                date = LocalDateTime.parse(entry.arguments!!.getString("date")!!).date,
                eventsModel = eventsModel,
                event = if (event == 0L) null else event,
            )
        }
    }
}
