package app.myzel394.planner.ui.components.overview.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import app.myzel394.planner.models.EventsModel
import app.myzel394.planner.ui.components.overview.widgets.AllDayEventsOverview
import app.myzel394.planner.ui.components.overview.widgets.EventsOverview
import app.myzel394.planner.ui.utils.pxToDp
import app.myzel394.planner.utils.formatDate
import kotlinx.datetime.LocalDate


@Composable
fun EventsPage(
    eventsModel: EventsModel,
    date: LocalDate,
    navController: NavController,
) {
    val events = eventsModel.collectAsSorted();
    val showAllDayEvents = rememberSaveable {
        mutableStateOf(false);
    }
    val allDayEvents = events.filter { it.isAllDay }
    val nonAllDayEvents = events.filter { !it.isAllDay }
    val windowHeight = pxToDp(LocalContext.current.resources.displayMetrics.heightPixels)
    val elementHeight = windowHeight / 12

    Column() {
        AnimatedVisibility(
            visible = showAllDayEvents.value,
            enter = expandVertically(),
            exit = shrinkVertically(
                spring(stiffness = Spring.StiffnessLow)
            ),
        ) {
            AllDayEventsOverview(
                events = allDayEvents,
                date = date,
                onShowAllDayChange = {
                    showAllDayEvents.value = !showAllDayEvents.value
                },
                onDelete = eventsModel::removeEvent,
            )
        }
        AnimatedVisibility(
            visible = !showAllDayEvents.value,
            enter = expandVertically(
                spring(stiffness = Spring.StiffnessHigh),
            ),
            exit = shrinkVertically(),
        ) {
            EventsOverview(
                allDayEvents = allDayEvents,
                nonAllDayEvents = nonAllDayEvents,
                navController = navController,
                eventsModel = eventsModel,
                elementHeight = elementHeight,
                onAllDayEventsVisibleChange = {
                    showAllDayEvents.value = !showAllDayEvents.value
                },
            )
        }
    }
}
