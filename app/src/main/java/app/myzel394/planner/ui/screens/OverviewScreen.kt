package app.myzel394.planner.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.myzel394.planner.models.EventsModel
import app.myzel394.planner.ui.Screen
import app.myzel394.planner.ui.components.widgets.AllDayEventsOverview
import app.myzel394.planner.ui.components.widgets.EventsOverview
import app.myzel394.planner.ui.utils.pxToDp
import app.myzel394.planner.utils.toISOString
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun OverviewScreen(
    navController: NavController,
    eventsModel: EventsModel,
) {
    val events = eventsModel.getAsSorted();
    val showAllDayEvents = remember {
        mutableStateOf(false);
    }
    val allDayEvents = events.filter { it.isAllDay }
    val nonAllDayEvents = events.filter { !it.isAllDay }
    val windowHeight = pxToDp(LocalContext.current.resources.displayMetrics.heightPixels)
    val elementHeight = windowHeight / 12

    Scaffold(
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = {
                    navController.navigate(
                        Screen.SaveEvent.withArgs(
                            toISOString(
                                Clock
                                    .System
                                    .now()
                                    .toLocalDateTime(TimeZone.currentSystemDefault()),
                            ),
                            0L.toString(),
                        ),
                    );
                }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            AnimatedVisibility(
                visible = showAllDayEvents.value,
                enter = expandVertically(),
                exit = shrinkVertically(
                    spring(stiffness = Spring.StiffnessLow)
                ),
            ) {
                AllDayEventsOverview(
                    events = allDayEvents,
                    date = Clock
                        .System
                        .now()
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                        .date,
                    onShowAllDayChange = {
                        showAllDayEvents.value = !showAllDayEvents.value;
                    },
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
                        showAllDayEvents.value = !showAllDayEvents.value;
                    },
                )
            }
        }
    }
}
