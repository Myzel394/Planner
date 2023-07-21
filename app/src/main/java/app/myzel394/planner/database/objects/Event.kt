package app.myzel394.planner.database.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import app.myzel394.planner.models.CreateEventModel
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
}
