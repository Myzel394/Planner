package app.myzel394.planner.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.myzel394.planner.models.EventsModel
import app.myzel394.planner.ui.Screen
import app.myzel394.planner.ui.components.overview.atoms.OverviewAppBar
import app.myzel394.planner.ui.components.overview.pages.EventsPage
import app.myzel394.planner.ui.utils.pxToDp
import app.myzel394.planner.utils.formatDate
import app.myzel394.planner.utils.toISOString
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun OverviewScreen(
    navController: NavController,
    eventsModel: EventsModel,
) {
    val coroutineScope = rememberCoroutineScope()
    val startDate =
        Clock.System
            .now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2,
    )
    val relativePage = pagerState.currentPage - Int.MAX_VALUE / 2
    val date = startDate
        .toJavaLocalDate()
        .plusDays(relativePage.toLong())
        .toKotlinLocalDate()

    Scaffold(
        topBar = {
             OverviewAppBar(
                 date = date,
                 onGoToDate = {
                     val distanceToToday = it.toJavaLocalDate().toEpochDay() - startDate.toJavaLocalDate().toEpochDay()
                     val index = Int.MAX_VALUE / 2 + distanceToToday.toInt()

                     coroutineScope.launch {
                         pagerState.animateScrollToPage(index)
                     }
                 },
             )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = {
                    navController.navigate(
                        Screen.SaveEvent.withArgs(
                            toISOString(date),
                            0L.toString(),
                        ),
                    );
                }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            HorizontalPager(
                state = pagerState,
                pageCount = Int.MAX_VALUE,
            ) {page ->
                val relativePage = page - Int.MAX_VALUE / 2
                val date = startDate
                    .toJavaLocalDate()
                    .plusDays(relativePage.toLong())
                    .toKotlinLocalDate()

                EventsPage(
                    eventsModel = eventsModel,
                    date = date,
                    navController = navController,
                )
            }
        }
    }
}
