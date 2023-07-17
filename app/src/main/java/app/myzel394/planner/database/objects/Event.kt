package app.myzel394.planner.database.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val date: LocalDate,
    val title: String = "",
    val description: String = "",
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
}
