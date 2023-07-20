package app.myzel394.planner.ui.utils

import app.myzel394.planner.database.objects.Event

// Returns a map of <LocalTime, Int> with the key being the start time of the event and the value
// being the divider by what the width should be divided by.
// This is used to properly align the events in the DayViewSchedule composable.
// Works for multiple events, meaning that 1, 2, 3 or more events can be passed in and it will
// return the correct dividers.
// This function will take a look at how many events are running simultaneously and will return
// the dividers for each event.
fun getDividers(events: List<Event>): Map<Event, Pair<Int, Int>> {
    val dividers = mutableMapOf<Event, Pair<Int, Int>>()
    var start = 0;
    var currentDivider = 1;

    for ((index, event) in events.withIndex()) {
        val nextEvent = if (index < events.size - 1) events[index + 1] else null;

        if (nextEvent == null || nextEvent.startTime > event.endTime) {
            val selectedEvents = events.subList(start, index + 1);

            selectedEvents.forEachIndexed { index, event ->
                dividers[event] = Pair(currentDivider, index);
            }

            currentDivider = 1;
            start = index + 1;
        } else {
            currentDivider++;
        }
    }

    return dividers;
}
