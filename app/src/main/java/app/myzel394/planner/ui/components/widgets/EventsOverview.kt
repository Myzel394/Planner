package app.myzel394.planner.ui.components.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import app.myzel394.planner.database.objects.Event
import app.myzel394.planner.models.CreateEventModel
import app.myzel394.planner.models.EventsModel
import app.myzel394.planner.ui.Screen
import app.myzel394.planner.ui.components.molecules.AddMoreEventsFooter
import app.myzel394.planner.ui.components.molecules.AllDayEventsHeader
import app.myzel394.planner.ui.components.molecules.EventsCalendar
import app.myzel394.planner.ui.utils.getDividers
import app.myzel394.planner.ui.utils.pxToDp
import app.myzel394.planner.utils.toISOString
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsOverview(
    events: List<Event>,
    navController: NavController,
    eventsModel: EventsModel,
) {
    val windowHeight = pxToDp(LocalContext.current.resources.displayMetrics.heightPixels);
    val elementHeight = windowHeight / 12;
    val scrollState = rememberScrollState();
    val allDayEvents = events.filter { it.isAllDay };
    val nonAllDayEvents = events.filter { !it.isAllDay };

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
        );
    }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        if (allDayEvents.isNotEmpty())
            AllDayEventsHeader(
                allDayEvents = events.filter { it.isAllDay },
                elementHeight = elementHeight,
                onShowAllDayChange = { /*TODO*/ },
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
