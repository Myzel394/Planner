package app.myzel394.planner.ui.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import app.myzel394.planner.constants.BIG_FAB_SIZE
import app.myzel394.planner.helpers.CalendarColors

@Composable
fun AddMoreEventsFooter() {
    val calendarColors = CalendarColors.default()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(BIG_FAB_SIZE + 32.dp)
            .drawBehind {
                drawLine(
                    color = calendarColors.line,
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
