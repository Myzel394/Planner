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
import kotlinx.datetime.LocalTime

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

    fun removeEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            AppDatabase.INSTANCE!!.eventDAO().delete(event);
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

        fun createEvent(
            createEventModel: CreateEventModel,
            date: LocalDate,
            isAllDay: Boolean = true,
        ): Event {
            return Event(
                title = createEventModel.title.value,
                description = createEventModel.description.value,
                startTime = when(isAllDay) {
                    true -> LocalTime(0, 0)
                        false -> createEventModel.startTime.value
                },
                endTime = when(isAllDay) {
                    true -> LocalTime(23, 59)
                        false -> createEventModel.endTime.value
                },
                date = date,
            )
        }

        fun createEvent(
            title: String,
            description: String,
            startTime: LocalTime,
            endTime: LocalTime,
            date: LocalDate,
        ): Event {
            return Event(
                title = title,
                description = description,
                startTime = startTime,
                endTime = endTime,
                date = date,
            )
        }
    }
}
