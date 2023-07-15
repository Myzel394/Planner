package app.myzel394.planner.ui.screens

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.myzel394.planner.models.CreateEventModel
import app.myzel394.planner.ui.utils.pxToDp
import app.myzel394.planner.ui.widgets.DayViewSchedule
import app.myzel394.planner.ui.widgets.DayViewScheduleSidebar
import app.myzel394.planner.ui.widgets.Event
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.datetime.LocalTime

val CALENDAR_HOUR_HEIGHT = 200.dp;

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(

    createEventModel: CreateEventModel = viewModel(),
) {
    val windowHeight = pxToDp(LocalContext.current.resources.displayMetrics.heightPixels);
    val elementHeight = windowHeight / 12;
    val startTimeDialog = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_12H)
        .setHour(createEventModel.startTime.value.hour)
        .setMinute(createEventModel.startTime.value.minute)
        .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
        .build();
    val scrollState = rememberScrollState();
    val lineColor = MaterialTheme.colorScheme.surfaceVariant;

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add",
                )

            }
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            CompositionLocalProvider(
                LocalOverscrollConfiguration provides null
            ) {
        Row() {
            DayViewScheduleSidebar(
                modifier = Modifier
                    .verticalScroll(scrollState),
                boxModifier = Modifier
                    .drawBehind {
                        // Bottom line
                        drawLine(
                            color = lineColor,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 1.dp.toPx(),
                        )
                    }
                    .padding(horizontal = 5.dp),
                height = elementHeight,
            ) {
                Text(
                    text = it.toString(),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                        )
                        .padding(horizontal = 6.dp)
                )
            }
            DayViewSchedule(
                events = listOf(
                    Event(
                        title = "Test",
                        startTime = LocalTime(12, 0),
                        endTime = LocalTime(13, 0),
                    ),
                ),
                eventHeight = elementHeight,
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState),
            ) { event ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(elementHeight)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                        ),
                ) {
                    Text(
                        text = event.title,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }
        }
        }
        }
    }
}
