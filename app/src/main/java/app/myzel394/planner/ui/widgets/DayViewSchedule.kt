package app.myzel394.planner.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalTime

data class Event(
    val title: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val color: Color? = null,
) {
    val durationInMinutes: Int
        get() = (endTime.hour - startTime.hour) * 60 + (endTime.minute - startTime.minute);

    fun getYOffset(baseHeight: Dp): Dp {
        return baseHeight.times(startTime.hour);
    }

    fun getHeight(baseHeight: Dp): Dp {
        val difference = durationInMinutes / 60f;

        return baseHeight.times(difference);
    }
}

@Composable
fun DayViewSchedule(
    modifier: Modifier = Modifier,
    hourBoxModifier: Modifier = Modifier,
    events: List<Event>,
    eventHeight: Dp = 200.dp,
) {
    Layout(
        content = {
            events.forEach { event ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(hourBoxModifier)
                        .height(eventHeight)
                ) {
                    Text(text = event.title)
                }
            }
        },
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .then(modifier)
    ){
        measurables, constraints ->
        val height = (24 * eventHeight.toPx()).toInt();
        val placeables = measurables.mapIndexed { index, measurable ->
            val event = events[index]

            Pair(
                measurable
                    .measure(
                        constraints.copy(
                            minHeight = event.getHeight(eventHeight).roundToPx(),
                            maxHeight = event.getHeight(eventHeight).roundToPx(),
                        ),
                    ),
                event,
            )
        }

        layout(constraints.maxWidth, height) {
            placeables.forEach { (placeable, event) ->
                placeable.placeRelative(0, event.getYOffset(eventHeight).roundToPx())
            }
        }
    }
}