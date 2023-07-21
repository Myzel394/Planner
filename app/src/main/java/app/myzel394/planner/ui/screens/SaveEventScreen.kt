package app.myzel394.planner.ui.screens

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddToPhotos
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.myzel394.planner.constants.ExampleDuration
import app.myzel394.planner.database.AppDatabase
import app.myzel394.planner.database.objects.Event
import app.myzel394.planner.models.CreateEventModel
import app.myzel394.planner.models.EventsModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun SaveEventScreen(
    navController: NavController,
    date: LocalDate,
    eventsModel: EventsModel,
    event: Long? = null,
    createEventModel: CreateEventModel = viewModel(),
) {
    val context = LocalContext.current;
    val fragmentManager = (LocalContext.current as AppCompatActivity).supportFragmentManager
    val focusRequester = remember { FocusRequester() }
    val eventInstance = remember {
        mutableStateOf<Event?>(null)
    }

    val isAllDay = remember {
        mutableStateOf(false)
    }

    fun saveEvent() {
        if (event == null) {
            val newEvent = EventsModel.createEvent(
                createEventModel,
                date,
                isAllDay.value,
            )

            eventsModel.insertEvent(newEvent)
        } else {
            eventInstance.value!!.applyModel(createEventModel);

            eventsModel.updateEvent(eventInstance.value!!)
        }
    }

    LaunchedEffect(Unit) {
        createEventModel.clear()
        focusRequester.requestFocus()

        if (event != null) {
            val eventValue = AppDatabase.INSTANCE!!.eventDAO().findById(event)

            eventValue.collectLatest {
                eventInstance.value = it

                createEventModel.applyEvent(it)
                isAllDay.value = it.isAllDay
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    PlainTooltipBox(
                        tooltip = {
                            Text("Close")
                        }
                    ) {
                        IconButton(
                            modifier = Modifier.tooltipAnchor(),
                            onClick = {
                                navController.popBackStack()
                            },
                        ) {
                            Icon(Icons.Filled.Close, "backIcon")
                        }
                    }
                },
                actions = {
                    if (event == null)
                        PlainTooltipBox(
                            tooltip = {
                                Text("Save & create another event")
                            },
                        ) {
                            IconButton(
                                modifier = Modifier.tooltipAnchor(),
                                onClick = {
                                    saveEvent();
                                    createEventModel.clear();
                                    focusRequester.requestFocus();

                                    Toast.makeText(
                                        context,
                                        "Event created",
                                        Toast.LENGTH_SHORT,
                                    ).show();
                                },
                            ) {
                                Icon(Icons.Filled.AddToPhotos, "checkIcon")
                            }
                        }
                    else
                        PlainTooltipBox(
                            tooltip = {
                                Text("Delete")
                            }
                        ) {
                            IconButton(
                                modifier = Modifier.tooltipAnchor(),
                                onClick = {
                                    eventsModel.removeEvent(eventInstance.value!!);
                                    navController.popBackStack()
                                },
                            ) {
                                Icon(
                                    Icons.Filled.DeleteForever,
                                    "deleteIcon",
                                )
                            }
                        }
                },
                title = {
                    Text(
                        if (event == null) "Create Event" else "Update ${eventInstance.value?.title}"
                    )
                },
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = createEventModel.title.value,
                onValueChange = createEventModel::setTitle,
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Title,
                        contentDescription = null,
                    )
                },
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable(
                            role = Role.Checkbox,
                            onClick = {
                                isAllDay.value = !isAllDay.value
                            }
                    ),
                ) {
                    Checkbox(
                        checked = isAllDay.value,
                        onCheckedChange = null,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "All day",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                    )
                }
                if (!isAllDay.value)
                    Spacer(modifier = Modifier.width(16.dp))
                AnimatedVisibility(
                    visible = !isAllDay.value,
                    enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessVeryLow)) + expandHorizontally(),
                    exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessMedium)) + shrinkHorizontally(),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Button(
                            onClick = {
                                val dialog = MaterialTimePicker.Builder()
                                    .setTimeFormat(TimeFormat.CLOCK_12H)
                                    .setHour(createEventModel.startTime.value.hour)
                                    .setMinute(createEventModel.startTime.value.minute)
                                    .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                                    .build()
                                dialog.addOnPositiveButtonClickListener {
                                    createEventModel.startTime.value =
                                        LocalTime(dialog.hour, dialog.minute)
                                }

                                dialog.show(fragmentManager, "startTime")
                            },
                            colors = ButtonDefaults.filledTonalButtonColors(),
                        ) {
                            Icon(
                                Icons.Filled.Schedule,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(ButtonDefaults.IconSize)
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text(createEventModel.formatStartTime())
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "to",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = {
                                val dialog = MaterialTimePicker.Builder()
                                    .setTimeFormat(TimeFormat.CLOCK_12H)
                                    .setHour(createEventModel.endTime.value.hour)
                                    .setMinute(createEventModel.endTime.value.minute)
                                    .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                                    .build()
                                dialog.addOnPositiveButtonClickListener {
                                    createEventModel.endTime.value =
                                        LocalTime(dialog.hour, dialog.minute)
                                }

                                dialog.show(fragmentManager, "endTime")
                            },
                            colors = ButtonDefaults.filledTonalButtonColors(),
                        ) {
                            Icon(
                                Icons.Filled.Schedule,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(ButtonDefaults.IconSize)
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text(createEventModel.formatEndTime())
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(
                visible = !isAllDay.value,
                enter = expandVertically(),
                exit = shrinkVertically(),
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    val durations = ExampleDuration
                        .predefinedDurationsWithTodayDate(
                            createEventModel
                                .startTime
                                .value
                                .atDate(date)
                                .toJavaLocalDateTime()
                        )

                    items(durations.size) { index ->
                        val duration = durations[index]

                        Button(
                            onClick = {
                                  createEventModel.endTime.value = createEventModel
                                      .startTime
                                      .value
                                      .toJavaLocalTime()
                                      .plusMinutes(duration.minutes.toLong())
                                      .toKotlinLocalTime()
                            },
                            colors = ButtonDefaults.textButtonColors(),
                        ) {
                            Text(duration.formatted())
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                leadingIcon = {
                    Icon(
                        Icons.Filled.Description,
                        contentDescription = null,
                    )
                },
                value = createEventModel.description.value,
                onValueChange = createEventModel::setDescription,
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                enabled = createEventModel.isValid(isAllDay.value),
                onClick = {
                    saveEvent()
                    navController.popBackStack()
                },
            ) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier
                        .size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    if (event == null) "Create" else "Save",
                )
            }
        }
    }
}
