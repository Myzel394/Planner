package app.myzel394.planner.ui.components.overview.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.myzel394.planner.constants.MIN_EVENT_HEIGHT
import app.myzel394.planner.database.objects.Event
import app.myzel394.planner.ui.components.overview.atoms.CalendarEventDayEntry
import app.myzel394.planner.ui.components.overview.atoms.DeleteForeverSwipeBackground
import app.myzel394.planner.ui.components.overview.atoms.EventDayEntry
import app.myzel394.planner.utils.formatDate
import kotlinx.datetime.LocalDate

val HEIGHT = 80.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllDayEventsOverview(
    events: List<Event>,
    date: LocalDate,
    onShowAllDayChange: () -> Unit,
    onDelete: (Event) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement
            .spacedBy(8.dp)
    ) {
        item {
            Text(
                formatDate(date),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }

        items(events.size) { index ->
            val event = events[index]
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

            SwipeToDismiss(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small),
                state = dismissState,
                directions = setOf(DismissDirection.StartToEnd),
                dismissContent = {
                    EventDayEntry(
                        event = event,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                },
                background = {
                    DeleteForeverSwipeBackground(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            )
        }

        item {
            Button(
                onClick = onShowAllDayChange,
                colors = ButtonDefaults.textButtonColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(HEIGHT)
            ) {
                Icon(
                    Icons.Filled.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier
                            .size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    "Close",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}
