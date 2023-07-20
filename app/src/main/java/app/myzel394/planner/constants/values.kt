package app.myzel394.planner.constants

import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime
import kotlin.time.Duration

data class ExampleDuration(
    val minutes: Int,
) {
    fun formatted(): String {
        val hours = minutes / 60;
        val minutes = minutes % 60;

        return when(hours) {
            0 -> {
                "${minutes}m"
            }
            else -> {
                when(minutes) {
                    0 -> {
                        "${hours}h"
                    }
                    else -> {
                        "${hours}h ${minutes}m"
                    }
                }
            }
        }
    }

    companion object {
        val PREDEFINED_DURATIONS = listOf(
            ExampleDuration(15),
            ExampleDuration(30),
            ExampleDuration(45),
            ExampleDuration(60),
            ExampleDuration(90),
            ExampleDuration(60 * 2),
            ExampleDuration(60 * 3),
            ExampleDuration(60 * 4),
            ExampleDuration(60 * 5),
            ExampleDuration(60 * 8),
            ExampleDuration(60 * 12),
        )

        fun predefinedDurationsWithTodayDate(date: LocalDateTime): List<ExampleDuration> {
            val originalDate = date.toKotlinLocalDateTime().date;

            return PREDEFINED_DURATIONS.filter {
                  date
                      .plusMinutes(it.minutes.toLong())
                      .toKotlinLocalDateTime()
                      .date == originalDate
            }
        }
    }
}
