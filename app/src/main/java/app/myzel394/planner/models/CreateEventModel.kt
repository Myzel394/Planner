package app.myzel394.planner.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import app.myzel394.planner.database.daos.EventDAO
import app.myzel394.planner.database.objects.Event
import app.myzel394.planner.utils.formatTime
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atDate
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.Duration

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

    fun setTitle(title: String) {
        this.title.value = title.trim().replace(Regex("[^A-Za-z0-9 ]"), "");
    }

    fun setDescription(description: String) {
        this.description.value = description.trim().replace(Regex("[^A-Za-z0-9 ]"), "");
    }

    fun formatStartTime(): String {
        return formatTime(startTime.value);
    }

    fun formatEndTime(): String {
        return formatTime(endTime.value);
    }

    fun formatDate(): String {
        return app.myzel394.planner.utils.formatDate(date.value);
    }

    fun formatTimeDistance(): String {
        val duration = Duration.between(
            startTime.value.atDate(date.value).toJavaLocalDateTime().toInstant(ZoneOffset.UTC),
            endTime.value.atDate(date.value).toJavaLocalDateTime().toInstant(ZoneOffset.UTC),
        );
        val minutes = duration.toMinutes() % 60;
        val hours = duration.toHours();

        if (hours == 0L) {
            return "${minutes}m";
        }

        return "${hours}h ${minutes}m";
    }

    val isValid: Boolean
        get() = title.value.isNotEmpty() && description.value.isNotEmpty() && startTime.value < endTime.value;
}
