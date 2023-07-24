package app.myzel394.planner.ui.components.overview.atoms

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.myzel394.planner.utils.formatDate
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
            IconButton(
                onClick = {
                    onGoToDate(
                        Clock
                            .System
                            .now()
                            .toLocalDateTime(TimeZone.currentSystemDefault())
                            .date
                    )
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
