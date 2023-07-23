package app.myzel394.planner.ui.components.save_event.atoms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycling
import app.myzel394.planner.constants.EXAMPLE_COLORS
import app.myzel394.planner.database.objects.EventColor
import app.myzel394.planner.database.objects.EventColors
import app.myzel394.planner.ui.components.common.atoms.ColorPickerDialog
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val SIZE = 40.dp
val SHAPE = CircleShape

@Composable
fun EventColorPicker(
    value: MutableState<EventColor>,
    customColorValue: MutableState<Color?>,
    onValueSelected: (EventColor) -> Unit,
    onCustomColorSelected: (Color) -> Unit,
) {
    val isCustomColorPredefined = EXAMPLE_COLORS.contains(customColorValue.value)
    var allColorsVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var showColorPicker by rememberSaveable {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val dividerColor = MaterialTheme.colorScheme.surfaceVariant

    SideEffect {
        allColorsVisible = allColorsVisible || customColorValue.value != null
    }

    if (showColorPicker)
        ColorPickerDialog(
            onColorSelected = {
                onCustomColorSelected(it)
                showColorPicker = false

                coroutineScope.launch {
                    delay(250L)
                    scrollState.animateScrollToItem(0)
                }
            },
            onDismissRequest = {
                showColorPicker = false
            }
        )
    LazyRow(
        horizontalArrangement = Arrangement
            .spacedBy(8.dp),
        state = scrollState,
    ) {
        // Subtract one because the last one is `EventColor.custom` and we want to render this
        // manually
        items(EventColor.values().size - 1) {
            val colorValue = EventColor.values()[it]
            val color = EventColors.getBackgroundMap()[colorValue]!!
            val isSelected = value.value == colorValue

            ColorSelect(
                color = color,
                isSelected = isSelected,
                onSelect = {
                    onValueSelected(colorValue)
                },
            )
        }

        if (allColorsVisible) {
            item {
                // Add a vertical divider
                Box(
                    modifier = Modifier
                        .padding(horizontal = SIZE / 2)
                        .height(SIZE)
                        .drawBehind {
                            drawLine(
                                color = dividerColor,
                                start = Offset(0f, 0f),
                                end = Offset(0f, size.height),
                                strokeWidth = 1.dp.toPx(),
                            )
                        }
                )
            }

            if (customColorValue.value != null && !isCustomColorPredefined)
                item {
                    ColorSelect(
                        color = customColorValue.value!!,
                        isSelected = true,
                    )
                }

            items(EXAMPLE_COLORS.size) {index ->
                val color = EXAMPLE_COLORS[index]
                val isSelected = customColorValue.value == color

                ColorSelect(
                    color = color,
                    isSelected = isSelected,
                    onSelect = {
                        onCustomColorSelected(color)
                    }
                )
            }

            item {
                val isBright = MaterialTheme.colorScheme.surfaceVariant.luminance() > 0.7f

                Box(
                    modifier = Modifier
                        .size(SIZE)
                        .clip(SHAPE)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable {
                            showColorPicker = true
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Filled.Brush,
                        contentDescription = null,
                        tint = if (isBright) Color.Black else Color.White,
                    )
                }
            }
        } else
            item {
                Box(
                    modifier = Modifier
                        .size(SIZE)
                        .clip(SHAPE)
                        .background(Color.Black)
                        .clickable {
                            allColorsVisible = !allColorsVisible
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = null,
                    )
                }
            }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ColorSelect(
    modifier: Modifier = Modifier,
    color: Color,
    onSelect: (() -> Unit)? = null,
    isSelected: Boolean,
) {
    val isBright = color.luminance() > 0.7f

    Box(
        modifier = Modifier
            .size(SIZE)
            .clip(SHAPE)
            .background(color)
            .then(
                if (onSelect == null) Modifier else Modifier.clickable(onClick = onSelect)
            )
            .then(modifier),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedVisibility(
            visible = isSelected,
            enter = scaleIn(),
            exit = scaleOut(),
        ) {
            Icon(
                Icons.Filled.Check,
                contentDescription = null,
                tint = if (isBright) Color.Black else Color.White,
            )
        }
    }
}
