package app.myzel394.planner.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import kotlin.math.roundToInt

@Composable
fun OffsetElement(
    modifier: Modifier = Modifier,
    offset: Offset,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, constraints ->
        val placeable = measurables.first().measure(constraints);

        layout(placeable.width, placeable.height) {
            placeable.placeRelative(
                x = (offset.x * placeable.width).roundToInt(),
                y = (offset.y * placeable.height).roundToInt(),
            );
        }
    }
}
