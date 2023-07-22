package app.myzel394.planner.helpers

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class CalendarColors(
    val line: Color,
) {
    companion object {
        @Composable
        fun default(): CalendarColors {
            return CalendarColors(
                line = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}
