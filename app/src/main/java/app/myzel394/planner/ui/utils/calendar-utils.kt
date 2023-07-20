package app.myzel394.planner.ui.utils

import kotlinx.datetime.LocalTime

class DayEntry(
    val startTime: LocalTime,
    val endTime: LocalTime,
) {
    val durationInMinutes: Int
        get() = (endTime.hour - startTime.hour) * 60 + (endTime.minute - startTime.minute);
}


// Returns a map of <LocalTime, Int> with the key being the start time of the event and the value
// being the divider by what the width should be divided by.
// This is used to properly align the events in the DayViewSchedule composable.
// Works for multiple events, meaning that 1, 2, 3 or more events can be passed in and it will
// return the correct dividers.
fun getDividers(events: List<DayEntry>): Map<LocalTime, Int> {
    val dividers = mutableMapOf<LocalTime, Int>();

    events.forEachIndexed { index, entry ->


    }
}
