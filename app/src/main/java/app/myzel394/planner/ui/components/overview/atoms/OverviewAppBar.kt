package app.myzel394.planner.ui.components.overview.atoms

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.myzel394.planner.ui.components.common.atoms.ExtendedIconButton
import app.myzel394.planner.utils.formatDate
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewAppBar(
    date: LocalDate,
    onGoToDate: (LocalDate) -> Unit,
) {
    val fragmentManager = (LocalContext.current as AppCompatActivity).supportFragmentManager

    TopAppBar(
        modifier = Modifier
            .shadow(4.dp),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
        ),
        title = {
            Text(
                text = formatDate(date),
                textAlign = TextAlign.Center,
            )
        },
        actions = {
            ExtendedIconButton(
                onClick = {
                    onGoToDate(
                        Clock
                            .System
                            .now()
                            .toLocalDateTime(TimeZone.currentSystemDefault())
                            .date
                    )
                },
                onLongClick = {
                    val datePicker =
                        MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Select date")
                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                            .build()
                    datePicker.addOnPositiveButtonClickListener {
                        onGoToDate(
                            LocalDate.fromEpochDays((it / 86400000).toInt())
                        )
                    }

                    datePicker.show(fragmentManager, "date")
                }
            ) {
                Icon(
                    Icons.Filled.Today,
                    contentDescription = "Today",
                )
            }
            IconButton(
                onClick = {
                    onGoToDate(
                        date
                            .toJavaLocalDate()
                            .minusDays(1)
                            .toKotlinLocalDate()
                    )
                }
            ) {
                Icon(
                    Icons.Filled.ChevronLeft,
                    contentDescription = "One day back",
                )
            }
            IconButton(
                onClick = {
                        onGoToDate(
                            date
                                .toJavaLocalDate()
                                .plusDays(1)
                                .toKotlinLocalDate()
                        )
                }
            ) {
                Icon(
                    Icons.Filled.ChevronRight,
                    contentDescription = "One day forward",
                )
            }
        }
    )
}
