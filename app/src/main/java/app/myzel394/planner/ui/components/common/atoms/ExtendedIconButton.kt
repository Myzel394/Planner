package app.myzel394.planner.ui.components.common.atoms

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

val SIZE = 40.dp
val SHAPE = CircleShape
const val DisabledIconOpacity = 0.38f

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExtendedIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    val containerColor: Color = Color.Transparent
    val contentColor: Color = LocalContentColor.current
    val disabledContainerColor: Color = Color.Transparent
    val disabledContentColor: Color = contentColor.copy(alpha = DisabledIconOpacity)

    val backgroundColor = if (enabled) containerColor else disabledContainerColor

    // Copied from `IconButton` with some modifications
    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .size(SIZE)
            .clip(SHAPE)
            .background(color = backgroundColor)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
                onDoubleClick = onDoubleClick,
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = SIZE / 2
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        val color = if (enabled) contentColor else disabledContentColor
        CompositionLocalProvider(LocalContentColor provides color, content = content)
    }
}

