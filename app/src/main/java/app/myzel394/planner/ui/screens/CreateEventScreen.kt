package app.myzel394.planner.ui.screens

import android.provider.CalendarContract.Colors
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddToPhotos
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.myzel394.planner.models.CreateEventModel
import app.myzel394.planner.utils.formatDate
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    navController: NavController,
    date: LocalDate,
    createEventModel: CreateEventModel = viewModel(),
) {
    val fragmentManager = (LocalContext.current as AppCompatActivity).supportFragmentManager;
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus();
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
                                navController.popBackStack();
                            },
                        ) {
                            Icon(Icons.Filled.Close, "backIcon")
                        }
                    }
                },
                actions = {
                    PlainTooltipBox(
                        tooltip = {
                            Text("Save & create another event")
                        },
                    ) {
                        IconButton(
                            modifier = Modifier.tooltipAnchor(),
                            onClick = {
                            },
                        ) {
                            Icon(Icons.Filled.AddToPhotos, "checkIcon")
                        }
                    }
                },
                title = {
                    Text("Create Event")
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
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    onClick = {
                        val dialog = MaterialTimePicker.Builder()
                            .setTimeFormat(TimeFormat.CLOCK_12H)
                            .setHour(createEventModel.startTime.value.hour)
                            .setMinute(createEventModel.startTime.value.minute)
                            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                            .build();
                        dialog.addOnPositiveButtonClickListener {
                            createEventModel.startTime.value = LocalTime(dialog.hour, dialog.minute);
                        }

                        dialog.show(fragmentManager, "startTime");
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
                            .build();
                        dialog.addOnPositiveButtonClickListener {
                            createEventModel.endTime.value = LocalTime(dialog.hour, dialog.minute);
                        }

                        dialog.show(fragmentManager, "endTime");
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
            Spacer(modifier = Modifier.height(32.dp))
            TextField(
                value = createEventModel.description.value,
                onValueChange = createEventModel::setDescription,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
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
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier
                        .size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Create")
            }
        }
    }
}
