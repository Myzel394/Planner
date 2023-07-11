package app.myzel394.planner.ui.utils

import android.content.res.Resources
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun pxToDp(
    px: Float
): Dp {
    return (px / Resources.getSystem().displayMetrics.density).dp;
}

fun pxToDp(
    px: Int
): Dp {
    return pxToDp(px.toFloat())
}

fun dpToPx(
    dp: Dp
): Float {
    return dp.value * Resources.getSystem().displayMetrics.density;
}
