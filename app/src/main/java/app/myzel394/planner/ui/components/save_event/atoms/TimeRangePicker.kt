package app.myzel394.planner.ui.components.save_event.atoms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import app.myzel394.planner.models.CreateEventModel
import app.myzel394.planner.ui.components.common.atoms.TimePicker

@Composable
fun TimeRangePicker(
    modifier: Modifier = Modifier,
    isAllDay: MutableState<Boolean>,
    createEventModel: CreateEventModel,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(
                    role = Role.Checkbox,
                    onClick = {
                        isAllDay.value = !isAllDay.value
                    }
                ),
        ) {
            Checkbox(
                checked = isAllDay.value,
                onCheckedChange = null,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "All day",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                ),
            )
        }
        if (!isAllDay.value)
            Spacer(modifier = Modifier.width(16.dp))
        AnimatedVisibility(
            visible = !isAllDay.value,
            enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessVeryLow)) + expandHorizontally(),
            exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessMedium)) + shrinkHorizontally(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TimePicker(
                    value = createEventModel.startTime.value,
                    onChange = { createEventModel.startTime.value = it },
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "to",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                    ),
                )
                Spacer(modifier = Modifier.width(16.dp))
                TimePicker(
                    value = createEventModel.endTime.value,
                    onChange = { createEventModel.endTime.value = it },
                )
            }
        }
    }
}