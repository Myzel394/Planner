package app.myzel394.planner.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.myzel394.planner.constants.MIN_EVENT_HEIGHT
import app.myzel394.planner.database.objects.Event
import app.myzel394.planner.helpers.CalendarColors
import app.myzel394.planner.ui.components.atoms.EventDayEntry
import kotlin.math.min

@Composable
fun AllDayEventsHeader(
    allDayEvents: List<Event>,
    elementHeight: Dp,
    onShowAllDayChange: () -> Unit,
    onGoToEvent: (Event) -> Unit,
) {
    val calendarColors = CalendarColors.default()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = calendarColors.line,
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
                        .height(elementHeight)
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(MaterialTheme.shapes.small)
                        .clickable(
                            onClick = {
                                onGoToEvent(event)
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
                    onClick = onShowAllDayChange,
                    modifier = Modifier
                        .height(elementHeight)
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
}