package app.myzel394.planner.ui.components.overview.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import app.myzel394.planner.database.objects.Event
import app.myzel394.planner.database.objects.EventColors

@Composable
fun CalendarEventDayEntry(
    baseHeight: Dp,
    minHeight: Dp,
    event: Event,
    modifier: Modifier = Modifier,
) {
    val height = max(minHeight, getHeight(baseHeight, event.durationInMinutes));
    val isSmall = height <= 40.dp;
    val colors = EventColors.fromEvent(event)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(color = colors.background)
            .padding(
                if (isSmall) 3.dp else 6.dp
            )
            .then(modifier),
    ) {
        Text(
            text = event.title,
            color = colors.title,
            fontSize = if (isSmall) 10.sp else MaterialTheme.typography.bodyMedium.fontSize,
        )
        if (!isSmall) {
            Text(
                text = event.description,
                color = colors.description,
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
            )
        }
    }
}