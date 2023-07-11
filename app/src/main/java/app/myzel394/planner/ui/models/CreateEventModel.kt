package app.myzel394.planner.ui.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

class CreateEventModel: ViewModel() {
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

}