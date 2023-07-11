package app.myzel394.planner.ui.utils

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.graphics.Shape

fun getBottomLineShape(bottomLineThickness: Float) : Shape {
    return GenericShape { size, _ ->
        // 1) Bottom-left corner
        moveTo(0f, size.height)
        // 2) Bottom-right corner
        lineTo(size.width, size.height)
        // 3) Top-right corner
        lineTo(size.width, size.height - bottomLineThickness)
        // 4) Top-left corner
        lineTo(0f, size.height - bottomLineThickness)
    }
}

fun getRightLineShape(rightLineThickness: Float) : Shape {
    return GenericShape { size, _ ->
        // 1) Bottom-left corner
        moveTo(0f, size.height)
        // 2) Bottom-right corner
        lineTo(size.width - rightLineThickness, size.height)
        // 3) Top-right corner
        lineTo(size.width - rightLineThickness, 0f)
        // 4) Top-left corner
        lineTo(0f, 0f)
    }
}