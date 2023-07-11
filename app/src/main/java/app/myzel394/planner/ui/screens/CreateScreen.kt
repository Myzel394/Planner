package app.myzel394.planner.ui.screens

import android.app.TimePickerDialog
import android.view.View
import android.widget.AbsoluteLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.input.InputMode
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.ScrollingView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import app.myzel394.planner.R
import app.myzel394.planner.ui.models.CreateEventModel
import app.myzel394.planner.ui.utils.getBottomLineShape
import app.myzel394.planner.ui.widgets.DayViewSchedule
import app.myzel394.planner.ui.widgets.Event
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.datetime.LocalTime

val CALENDAR_HOUR_HEIGHT = 200.dp;

@Composable
fun CreateScreen(
    createEventModel: CreateEventModel = viewModel(),
) {
    Box {
        DayViewSchedule(
            events = listOf(
                Event(
                    title = "Test",
                    startTime = LocalTime(12, 0),
                    endTime = LocalTime(13, 0),
                ),
            ),
            hourBoxModifier = Modifier.background(
                color = MaterialTheme.colorScheme.primary,
            ),
        )
    }
}
