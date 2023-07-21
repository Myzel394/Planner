package app.myzel394.planner.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.myzel394.planner.models.EventsModel
import app.myzel394.planner.ui.Screen
import app.myzel394.planner.ui.utils.getDividers
import app.myzel394.planner.ui.utils.pxToDp
import app.myzel394.planner.ui.widgets.DayViewSchedule
import app.myzel394.planner.ui.widgets.DayViewScheduleSidebar
import app.myzel394.planner.ui.widgets.EventDayEntry
import app.myzel394.planner.utils.toISOString
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.lang.Integer.min

val FAB_SIZE = 96.dp;
val MIN_EVENT_HEIGHT = 20.dp;

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    navController: NavController,
    eventsModel: EventsModel,
) {
    val windowWidth = pxToDp(LocalContext.current.resources.displayMetrics.widthPixels);
    val windowHeight = pxToDp(LocalContext.current.resources.displayMetrics.heightPixels);
    val elementHeight = windowHeight / 12;
    val scrollState = rememberScrollState();
    val lineColor = MaterialTheme.colorScheme.surfaceVariant;
    val events = eventsModel.getAsSorted();
    val nonAllDayEvents = events.filter { !it.isAllDay };
    val allDayEvents = events.filter { it.isAllDay };
    val eventDividers = getDividers(nonAllDayEvents);

    println(eventDividers);

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
        Box(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                if (allDayEvents.isNotEmpty())
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(elementHeight)
                            .drawBehind {
                                drawLine(
                                    color = lineColor,
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 1.dp.toPx(),
                                )
                            }
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(5.dp)
                        ) {
                            val visibleEvents = allDayEvents
                                .subList(
                                    0,
                                    min(
                                        if (allDayEvents.size > 4) 3 else 4,
                                        allDayEvents.size,
                                    ),
                                )

                            for (event in visibleEvents) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .clip(MaterialTheme.shapes.small)
                                        .clickable(
                                            onClick = {
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
                                        )
                                ) {
                                    EventDayEntry(
                                        baseHeight = elementHeight,
                                        event = event,
                                        minHeight = MIN_EVENT_HEIGHT,
                                    )
                                }
                            }
                            if (allDayEvents.size > 4)
                                IconButton(
                                    onClick = {},
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .clip(MaterialTheme.shapes.small)
                                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                                ) {
                                    Icon(
                                        Icons.Filled.Add,
                                        contentDescription = null,
                                    )
                                }
                        }
                    }
                Row {
                    DayViewScheduleSidebar(
                        boxModifier = Modifier
                            .drawBehind {
                                // Bottom line
                                drawLine(
                                    color = lineColor,
                                    start = Offset(0f, 0f),
                                    end = Offset(size.width, 0f),
                                    strokeWidth = 1.dp.toPx(),
                                )
                            }
                            .padding(horizontal = 5.dp),
                        height = elementHeight,
                    ) {
                        Text(
                            text = it.toString(),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.surface,
                                )
                                .padding(horizontal = 6.dp)
                        )
                    }
                    DayViewSchedule(
                        events = nonAllDayEvents,
                        eventHeight = elementHeight,
                        minHeight = MIN_EVENT_HEIGHT,
                        modifier = Modifier
                            .weight(1f),
                    ) { event ->
                        val dismissState = rememberDismissState(
                            confirmValueChange = { dismissValue ->
                                when (dismissValue) {
                                    DismissValue.DismissedToEnd -> {
                                        eventsModel.removeEvent(event)
                                    }
                                    else -> {}
                                }
                                false
                            }
                        )
                        val (divider, index) = eventDividers[event]!!;
                        var size by remember { mutableStateOf(IntSize.Zero) }

                        Box(
                            modifier = Modifier.onSizeChanged {
                                size = it;
                            },
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(
                                        start = pxToDp(size.width).times(1f / divider * index)
                                    )
                                    .width(
                                        pxToDp(size.width).times(1f / divider)
                                    )
                                    .clickable(
                                        onClick = {
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
                                    )
                            ) {
                                SwipeToDismiss(
                                    state = dismissState,
                                    directions = setOf(DismissDirection.StartToEnd),
                                    dismissContent = {
                                        EventDayEntry(
                                            baseHeight = elementHeight,
                                            event = event,
                                            minHeight = MIN_EVENT_HEIGHT,
                                        )
                                    },
                                    background = {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(MaterialTheme.shapes.small)
                                                .padding(6.dp)
                                        ) {
                                            Icon(
                                                Icons.Filled.DeleteForever,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onErrorContainer,
                                                modifier = Modifier
                                                    .align(Alignment.CenterStart),
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
                if (events.isNotEmpty())
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(FAB_SIZE + 32.dp)
                            .drawBehind {
                                drawLine(
                                    color = lineColor,
                                    start = Offset(0f, 0f),
                                    end = Offset(size.width, 0f),
                                    strokeWidth = 1.dp.toPx(),
                                )
                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize(),
                        ) {
                            Text(
                                "Add another event",
                                style = MaterialTheme.typography.bodySmall,
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Icon(
                                Icons.Filled.ChevronRight,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(ButtonDefaults.IconSize)
                            )
                        }
                    }
            }
        }
    }
}
