package app.myzel394.planner.database.objects

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.myzel394.planner.models.CreateEventModel
import com.godaddy.android.colorpicker.HsvColor
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
    // No idea why, but using a color converter doesn't work
    var customColorRaw: Int? = null,
) {
    var customColor: Color?
        set(value) {
            customColorRaw = value?.toArgb()
        }
        get() = customColorRaw?.let { Color(it) }

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
        color = createEventModel.color.value;
        customColorRaw = createEventModel.customColor.value?.toArgb();
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
        fun getBackgroundMap(): Map<EventColor, Color> = mapOf(
            EventColor.primary to MaterialTheme.colorScheme.primaryContainer,
            EventColor.secondary to MaterialTheme.colorScheme.secondaryContainer,
            EventColor.tertiary to MaterialTheme.colorScheme.tertiaryContainer,
            EventColor.custom to Color(0xFF000000),
        )

        @Composable
        fun getTitleMap(): Map<EventColor, Color> = mapOf(
            EventColor.primary to MaterialTheme.colorScheme.onPrimaryContainer,
            EventColor.secondary to MaterialTheme.colorScheme.onSecondaryContainer,
            EventColor.tertiary to MaterialTheme.colorScheme.onTertiaryContainer,
            EventColor.custom to Color(0xFFFFFFFF),
        )

        @Composable
        fun getDescriptionMap(): Map<EventColor, Color> = mapOf(
            EventColor.primary to MaterialTheme.colorScheme.secondary,
            EventColor.secondary to MaterialTheme.colorScheme.tertiary,
            EventColor.tertiary to MaterialTheme.colorScheme.onSurface,
            EventColor.custom to Color(0xFFAAAAAA),
        )

        @Composable
        fun fromEvent(event: Event) = EventColors(
            background = when (event.color) {
                EventColor.custom -> {
                    val hsv = HsvColor.from(event.customColor!!)

                    hsv.copy(value = hsv.value * 0.3f).toColor()
                }
                else -> getBackgroundMap()[event.color]!!
            },
            title = when (event.color) {
                EventColor.custom -> event.customColor!!
                else -> getTitleMap()[event.color]!!
            },
            description = when (event.color) {
                EventColor.custom -> {
                    val hsv = HsvColor.from(event.customColor!!)

                    hsv.copy(value = hsv.value * 0.8f).toColor()
                }
                else -> getDescriptionMap()[event.color]!!
            }
        )
    }
}
