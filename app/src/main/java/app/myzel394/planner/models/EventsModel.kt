package app.myzel394.planner.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import app.myzel394.planner.database.AppDatabase
import app.myzel394.planner.database.daos.EventDAO
import app.myzel394.planner.database.objects.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate

class EventsModel : ViewModel() {
    var events by mutableStateOf(
        runBlocking {
            AppDatabase.INSTANCE!!.eventDAO().getAll()
        }
    )

    fun insertEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            event.id = AppDatabase.INSTANCE!!.eventDAO().insert(event);
        }
    }

    companion object {
        fun createEvent(createEventModel: CreateEventModel, date: LocalDate): Event {
            return Event(
                title = createEventModel.title.value,
                description = createEventModel.description.value,
                startTime = createEventModel.startTime.value,
                endTime = createEventModel.endTime.value,
                date = date,
            )
        }
    }
}
