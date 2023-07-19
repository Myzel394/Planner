package app.myzel394.planner.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.myzel394.planner.database.objects.Event

@Composable
fun EventDayEntry(
    height: Dp,
    event: Event,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
            )
            .padding(6.dp)
            .then(modifier),
    ) {
        Text(
            text = event.title,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}