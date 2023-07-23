package app.myzel394.planner.ui.components.save_event.atoms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import app.myzel394.planner.constants.ExampleDuration
import app.myzel394.planner.models.CreateEventModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.atDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalTime


@Composable
fun DurationSelect(
    createEventModel: CreateEventModel,
    date: LocalDate,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val durations = ExampleDuration
            .predefinedDurationsWithTodayDate(
                createEventModel
                    .startTime
                    .value
                    .atDate(date)
                    .toJavaLocalDateTime()
            )

        items(durations.size) { index ->
            val duration = durations[index]

            Button(
                onClick = {
                    createEventModel.endTime.value = createEventModel
                        .startTime
                        .value
                        .toJavaLocalTime()
                        .plusMinutes(duration.minutes.toLong())
                        .toKotlinLocalTime()
                },
                colors = ButtonDefaults.textButtonColors(),
            ) {
                Text(duration.formatted())
            }
        }
    }
}
