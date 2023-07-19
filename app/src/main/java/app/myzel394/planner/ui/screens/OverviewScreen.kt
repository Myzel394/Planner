package app.myzel394.planner.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.myzel394.planner.models.CreateEventModel
import app.myzel394.planner.models.EventsModel
import app.myzel394.planner.ui.Screen
import app.myzel394.planner.ui.utils.pxToDp
import app.myzel394.planner.ui.widgets.DayViewSchedule
import app.myzel394.planner.ui.widgets.DayViewScheduleSidebar
import app.myzel394.planner.utils.toISOString
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.flow.count
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

val CALENDAR_HOUR_HEIGHT = 200.dp;
val FAB_SIZE = 96.dp;

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    navController: NavController,
    eventsModel: EventsModel,
) {
    val windowHeight = pxToDp(LocalContext.current.resources.displayMetrics.heightPixels);
    val elementHeight = windowHeight / 12;
    val scrollState = rememberScrollState();
    val lineColor = MaterialTheme.colorScheme.surfaceVariant;
    val events = eventsModel.events.collectAsState(initial = listOf()).value;

    Scaffold(
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = {
                    navController.navigate(
                        Screen.CreateEvent.withArgs(
                            toISOString(
                                Clock
                                    .System
                                    .now()
                                    .toLocalDateTime(TimeZone.currentSystemDefault()),
                            )
                        ),
                    );
                }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                Row {
                    DayViewScheduleSidebar(
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
                        events = events,
                        eventHeight = elementHeight,
                        modifier = Modifier
                            .weight(1f),
                    ) { event ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(elementHeight)
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(
                                        alpha = 0.2f,
                                    )
                                )
                                .padding(6.dp),
                        ) {
                            Text(
                                text = event.title,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(FAB_SIZE + 32.dp)
                        .drawBehind {
                            drawLine(
                                color = lineColor,
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
                            "Add an event",
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
        }
    }
}
