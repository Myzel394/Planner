package app.myzel394.planner.ui.components.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import app.myzel394.planner.database.objects.Event
import app.myzel394.planner.models.EventsModel
import app.myzel394.planner.ui.Screen
import app.myzel394.planner.ui.components.molecules.AddMoreEventsFooter
import app.myzel394.planner.ui.components.molecules.AllDayEventsHeader
import app.myzel394.planner.ui.components.molecules.EventsCalendar
import app.myzel394.planner.utils.toISOString
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun EventsOverview(
    allDayEvents: List<Event>,
    nonAllDayEvents: List<Event>,
    elementHeight: Dp,
    navController: NavController,
    eventsModel: EventsModel,
    onAllDayEventsVisibleChange: () -> Unit,
) {
    val scrollState = rememberScrollState()

    fun goToEvent(event: Event): Unit {
        navController.navigate(
            Screen.SaveEvent.withArgs(
                toISOString(
                    Clock
                        .System
                        .now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                ),
                event.id.toString(),
            ),
        )
    }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        if (allDayEvents.isNotEmpty())
            AllDayEventsHeader(
                allDayEvents = allDayEvents,
                elementHeight = elementHeight,
                onShowAllDayChange = onAllDayEventsVisibleChange,
                onGoToEvent = {
                    goToEvent(it)
                }
            )
        EventsCalendar(
            elementHeight = elementHeight,
            events = nonAllDayEvents,
            onDelete = eventsModel::removeEvent,
            onGoToEvent = {
                goToEvent(it)
            }
        )
        if (nonAllDayEvents.isNotEmpty())
            AddMoreEventsFooter()
    }
}
