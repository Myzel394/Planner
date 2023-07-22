package app.myzel394.planner.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import app.myzel394.planner.constants.MIN_EVENT_HEIGHT
import app.myzel394.planner.database.objects.Event
import app.myzel394.planner.helpers.CalendarColors
import app.myzel394.planner.ui.utils.getDividers
import app.myzel394.planner.ui.utils.pxToDp
import app.myzel394.planner.ui.components.atoms.DayViewSchedule
import app.myzel394.planner.ui.components.atoms.DayViewScheduleSidebar
import app.myzel394.planner.ui.components.atoms.EventDayEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsCalendar(
    elementHeight: Dp,
    events: List<Event>,
    onDelete: (Event) -> Unit,
    onGoToEvent: (Event) -> Unit,
) {
    val calendarColors = CalendarColors.default()
    val eventDividers = getDividers(events)

    Row {
        DayViewScheduleSidebar(
            boxModifier = Modifier
                .drawBehind {
                    // Bottom line
                    drawLine(
                        color = calendarColors.line,
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
            events = events,
            eventHeight = elementHeight,
            minHeight = MIN_EVENT_HEIGHT,
            modifier = Modifier
                .weight(1f),
        ) { event ->
            val dismissState = rememberDismissState(
                confirmValueChange = { dismissValue ->
                    when (dismissValue) {
                        DismissValue.DismissedToEnd -> {
                            onDelete(event)
                        }

                        else -> {}
                    }
                    false
                }
            )
            val (divider, index) = eventDividers[event]!!
            var size by remember { mutableStateOf(IntSize.Zero) }

            Box(
                modifier = Modifier.onSizeChanged {
                    size = it
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
                                onGoToEvent(event)
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
}
