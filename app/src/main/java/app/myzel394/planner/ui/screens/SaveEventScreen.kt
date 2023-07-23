package app.myzel394.planner.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddToPhotos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.myzel394.planner.database.AppDatabase
import app.myzel394.planner.database.objects.Event
import app.myzel394.planner.database.objects.EventColor
import app.myzel394.planner.models.CreateEventModel
import app.myzel394.planner.models.EventsModel
import app.myzel394.planner.ui.components.common.atoms.SingleActionButton
import app.myzel394.planner.ui.components.save_event.atoms.DurationSelect
import app.myzel394.planner.ui.components.save_event.atoms.EventColorPicker
import app.myzel394.planner.ui.components.save_event.atoms.TimeRangePicker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.LocalDate

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
    val focusRequester = remember { FocusRequester() }
    val eventInstance = rememberSaveable {
        mutableStateOf<Event?>(null)
    }

    val isAllDay = rememberSaveable {
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
            TimeRangePicker(isAllDay = isAllDay, createEventModel = createEventModel)
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(
                visible = !isAllDay.value,
                enter = expandVertically(),
                exit = shrinkVertically(),
            ) {
                DurationSelect(createEventModel = createEventModel, date = date)
            }
            Spacer(modifier = Modifier.height(32.dp))
            EventColorPicker(
                value = createEventModel.color,
                customColorValue = createEventModel.customColor,
                onValueSelected = {
                    createEventModel.color.value = it
                    createEventModel.customColor.value = null
                },
                onCustomColorSelected = {
                    createEventModel.color.value = EventColor.custom
                    createEventModel.customColor.value = it
                },
            )
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
            SingleActionButton(
                onClick = {
                    saveEvent()
                    navController.popBackStack()
                },
                label = if (event == null) "Create" else "Update"
            )
        }
    }
}
