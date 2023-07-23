package app.myzel394.planner.ui.components.common.atoms

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import app.myzel394.planner.utils.formatTime
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.datetime.LocalTime

@Composable
fun TimePicker(
    value: LocalTime?,
    onChange: (LocalTime) -> Unit,
) {
    val fragmentManager = (LocalContext.current as AppCompatActivity).supportFragmentManager

    Button(
        onClick = {
            val dialog = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(value?.hour ?: 8)
                .setMinute(value?.minute ?: 0)
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .build()
            dialog.addOnPositiveButtonClickListener {
                onChange(LocalTime(dialog.hour, dialog.minute))
            }

            dialog.show(fragmentManager, "startTime")
        },
        colors = ButtonDefaults.filledTonalButtonColors(),
    ) {
        Icon(
            Icons.Filled.Schedule,
            contentDescription = null,
            modifier = Modifier
                .size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(
            if (value == null)
                "Select time"
            else
            formatTime(value)
        )
    }
}