package app.myzel394.planner.ui

sealed class Screen(val route: String) {
    object Overview : Screen("overview")
    object CreateEvent : Screen("create-event")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}