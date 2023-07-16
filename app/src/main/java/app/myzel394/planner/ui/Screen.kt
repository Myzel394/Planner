package app.myzel394.planner.ui

sealed class Screen(val route: String) {
    object Overview : Screen("overview")
    object CreateEvent : Screen("create_event")
}