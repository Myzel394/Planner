package app.myzel394.planner.ui.components.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.myzel394.planner.constants.MIN_EVENT_HEIGHT
import app.myzel394.planner.database.objects.Event
import app.myzel394.planner.ui.components.atoms.EventDayEntry
import app.myzel394.planner.utils.formatDate
import kotlinx.datetime.LocalDate

val HEIGHT = 80.dp

@Composable
fun AllDayEventsOverview(
    events: List<Event>,
    date: LocalDate,
    onShowAllDayChange: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
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

            Box(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .height(HEIGHT)
            ) {
                EventDayEntry(
                    baseHeight = HEIGHT,
                    minHeight = MIN_EVENT_HEIGHT,
                    event = event,
                )
            }
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
