package app.myzel394.planner.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import app.myzel394.planner.database.AppDatabase

class FetchEventModel(
    val event: Long,
) : ViewModel() {
    var eventInstance by mutableStateOf(
        AppDatabase.INSTANCE!!.eventDAO().findById(event)
    )
}
