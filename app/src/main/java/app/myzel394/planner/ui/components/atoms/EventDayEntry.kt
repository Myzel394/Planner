package app.myzel394.planner.ui.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.myzel394.planner.database.objects.Event
import app.myzel394.planner.database.objects.EventColors


@Composable
fun EventDayEntry(
    modifier: Modifier = Modifier,
    event: Event,
) {
    val colors = EventColors.fromEvent(event)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colors.background)
            .padding(6.dp)
            .then(modifier),
    ) {
        Text(
            text = event.title,
            color = colors.title,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        )
        Text(
            text = event.description,
            color = colors.description,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
        )
    }
}
