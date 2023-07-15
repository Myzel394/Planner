package app.myzel394.planner.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import app.myzel394.planner.database.daos.EventDAO
import app.myzel394.planner.database.objects.Event
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

class CreateEventModel(): ViewModel() {
    var startTime = mutableStateOf<LocalTime>(
        Clock
            .System
            .now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .time,
    );
    var endTime = mutableStateOf<LocalTime>(
        Clock
            .System
            .now()
            .plus(1, DateTimeUnit.HOUR, TimeZone.currentSystemDefault())
            .toLocalDateTime(TimeZone.currentSystemDefault()).time,
    );
    var date = mutableStateOf<LocalDate>(
        Clock
            .System
            .now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date,
    );
    var title = mutableStateOf<String>("");
    var description = mutableStateOf<String>("");
}
