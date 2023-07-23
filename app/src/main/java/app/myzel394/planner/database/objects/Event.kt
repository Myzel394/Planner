package app.myzel394.planner.database.objects

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import app.myzel394.planner.models.CreateEventModel
import com.google.android.material.color.utilities.MaterialDynamicColors.background
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var startTime: LocalTime,
    var endTime: LocalTime,
    var date: LocalDate,
    var title: String = "",
    var description: String = "",
    var color: EventColor = EventColor.primary,
) {
    val startDateTime
        get() = LocalDateTime(
            date.year,
            date.month,
            date.dayOfMonth,
            startTime.hour,
            startTime.minute,
        );

    val endDateTime
        get() = LocalDateTime(
            date.year,
            date.month,
            date.dayOfMonth,
            endTime.hour,
            endTime.minute,
        );

    val durationInMinutes: Int
        get() = (endTime.hour - startTime.hour) * 60 + (endTime.minute - startTime.minute);

    fun applyModel(createEventModel: CreateEventModel) {
        title = createEventModel.title.value;
        description = createEventModel.description.value;
        startTime = createEventModel.startTime.value;
        endTime = createEventModel.endTime.value;
        date = createEventModel.date.value;
    }

    val isAllDay: Boolean
        get() = startTime == LocalTime(0, 0) && endTime == LocalTime(23, 59);
}

enum class EventColor {
    primary,
    secondary,
    tertiary,
    custom,
}

data class EventColors(
    val background: Color,
    val title: Color,
    val description: Color,
) {
    companion object {
        @Composable
        fun fromEvent(event: Event) = EventColors(
            background = when (event.color) {
                EventColor.primary -> MaterialTheme.colorScheme.primaryContainer
                EventColor.secondary -> MaterialTheme.colorScheme.secondaryContainer
                EventColor.tertiary -> MaterialTheme.colorScheme.tertiaryContainer
                EventColor.custom -> Color(0x000000)
            },
            title = when (event.color) {
                EventColor.primary -> MaterialTheme.colorScheme.onPrimaryContainer
                EventColor.secondary -> MaterialTheme.colorScheme.onSecondaryContainer
                EventColor.tertiary -> MaterialTheme.colorScheme.onTertiaryContainer
                EventColor.custom -> Color(0xFFFFFF)
            },
            description = when (event.color) {
                EventColor.primary -> MaterialTheme.colorScheme.secondary
                EventColor.secondary -> MaterialTheme.colorScheme.tertiary
                EventColor.tertiary -> MaterialTheme.colorScheme.onSurface
                EventColor.custom -> Color(0x000000)
            }
        )
    }
}
