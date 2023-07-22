package app.myzel394.planner.ui.components.atoms

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import app.myzel394.planner.database.objects.Event
import kotlinx.datetime.LocalTime

fun getYOffset(baseHeight: Dp, startTime: LocalTime): Dp {
    return baseHeight.times(startTime.hour + startTime.minute / 60f);
}

fun getHeight(baseHeight: Dp, durationInMinutes: Int): Dp {
    val difference = durationInMinutes / 60f;

    return baseHeight.times(difference);
}

@Composable
fun DayViewSchedule(
    modifier: Modifier = Modifier,
    events: List<Event>,
    eventHeight: Dp = 200.dp,
    minHeight: Dp,
    renderBox: @Composable (Event) -> Unit
) {
    val lineColor = MaterialTheme.colorScheme.surfaceVariant;

    Layout(
        content = {
            events.forEach {
                renderBox(it);
            }
        },
        modifier = Modifier
            .then(modifier)
            .drawBehind {
                repeat(23) {
                    drawLine(
                        lineColor,
                        start = Offset(0f, (it + 1) * eventHeight.toPx()),
                        end = Offset(size.width, (it + 1) * eventHeight.toPx()),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }
    ){
        measurables, constraints ->
        val height = (24 * eventHeight.toPx()).toInt();
        val placeables = measurables.mapIndexed { index, measurable ->
            val event = events[index]

            Pair(
                measurable
                    .measure(
                        constraints.copy(
                            minHeight = max(minHeight, getHeight(eventHeight, event.durationInMinutes)).roundToPx(),
                            maxHeight = max(minHeight, getHeight(eventHeight, event.durationInMinutes)).roundToPx(),
                        ),
                    ),
                event,
            )
        }

        layout(constraints.maxWidth, height) {
            placeables.forEach { (placeable, event) ->
                placeable.placeRelative(0, getYOffset(eventHeight, event.startTime).roundToPx())
            }
        }
    }
}
