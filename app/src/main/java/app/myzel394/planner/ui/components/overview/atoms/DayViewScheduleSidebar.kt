package app.myzel394.planner.ui.components.overview.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalTime

@Composable
fun DayViewScheduleSidebar(
    modifier: Modifier = Modifier,
    boxModifier: Modifier = Modifier,
    height: Dp,
    renderBox: @Composable (LocalTime) -> Unit
) {
    val lineColor = MaterialTheme.colorScheme.surfaceVariant;
    val rightDrawModifier = Modifier
        .drawBehind {
            // Right line
            drawLine(
                color = lineColor,
                start = Offset(size.width, 0f),
                end = Offset(size.width, size.height),
                strokeWidth = 1.dp.toPx(),
            )
        }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
    ) {
        repeat(24) { hour ->
            when (hour) {
                0 -> Box(
                    modifier = rightDrawModifier
                        .background(color = MaterialTheme.colorScheme.tertiary)
                        .height(height)
                ) {
                }
                else -> Box(
                    modifier = rightDrawModifier
                        .height(height)
                        .then(boxModifier)
                ) {
                    OffsetElement(
                        offset = Offset(0F, -0.5F),
                        content = {
                            renderBox(LocalTime(hour, 0))
                        },
                    )
                }
            }
        }
    }
}