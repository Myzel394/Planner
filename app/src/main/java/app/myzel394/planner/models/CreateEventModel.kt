package app.myzel394.planner.models

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import app.myzel394.planner.constants.DOUBLE_SPACES_REGEX
import app.myzel394.planner.constants.NON_ASCII_REGEX
import app.myzel394.planner.database.daos.EventDAO
import app.myzel394.planner.database.objects.Event
import app.myzel394.planner.database.objects.EventColor
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
    var color = mutableStateOf<EventColor>(EventColor.primary);
    var customColor = mutableStateOf<Color?>(null)

    fun setTitle(title: String) {
        this.title.value = title.replace(DOUBLE_SPACES_REGEX, " ").replace(NON_ASCII_REGEX, "");
    }

    fun setDescription(description: String) {
        this.description.value = description.replace(DOUBLE_SPACES_REGEX, " ").replace(NON_ASCII_REGEX, "");
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

    fun isValid(isAllDay: Boolean = false): Boolean =
        title.value.isNotEmpty()
                && (isAllDay || startTime.value < endTime.value)
                && (color.value != EventColor.custom || customColor.value != null)

    fun clear() {
        startTime.value = Clock
            .System
            .now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .time;
        endTime.value = Clock
            .System
            .now()
            .plus(1, DateTimeUnit.HOUR, TimeZone.currentSystemDefault())
            .toLocalDateTime(TimeZone.currentSystemDefault()).time;
        date.value = Clock
            .System
            .now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date;
        title.value = "";
        description.value = "";
        color.value = EventColor.primary;
        customColor.value = null;
    }

    fun applyEvent(event: Event) {
        startTime.value = event.startTime;
        endTime.value = event.endTime;
        date.value = event.date;
        title.value = event.title;
        description.value = event.description;
        color.value = event.color;
        customColor.value = event.customColor;
    }

    companion object {
        fun fromEvent(event: Event): CreateEventModel {
            val createEventModel = CreateEventModel();

            createEventModel.title.value = event.title;
            createEventModel.description.value = event.description;
            createEventModel.startTime.value = event.startTime;
            createEventModel.endTime.value = event.endTime;
            createEventModel.date.value = event.date;
            createEventModel.color.value = event.color;
            createEventModel.customColor.value = event.customColor;

            return createEventModel;
        }
    }
}
