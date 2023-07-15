package app.myzel394.planner.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import app.myzel394.planner.database.daos.EventDAO
import app.myzel394.planner.database.objects.Event
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EventsModel(
    private val eventDao: EventDAO,
): ViewModel() {
    var events by mutableStateOf(
        runBlocking {
            eventDao.getAll()
        }
    )

    fun insertEvent(event: Event) {
        viewModelScope.launch {
            event.id = eventDao.insert(event);
        }
    }

    fun createEvent(createEventModel: CreateEventModel): Event {
         return Event(
            title = createEventModel.title.value,
            description = createEventModel.description.value,
            startTime = createEventModel.startTime.value,
            endTime = createEventModel.endTime.value,
            date = createEventModel.date.value,
        )
    }
}

class EventModelFactory(
    private val eventDao: EventDAO,
): ViewModelProvider.Factory {
    fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateEventModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventsModel(eventDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
