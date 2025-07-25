import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SymptomChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isPressed by remember { mutableStateOf(false) }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            isPressed = interaction is androidx.compose.foundation.interaction.PressInteraction.Press
        }
    }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        label = "chip-press-scale"
    )

    InputChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text) },
        trailingIcon = {
            if (selected) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove"
                )
            }
        },
        colors = InputChipDefaults.inputChipColors(
            containerColor = if (selected) Color(0xFFD32F2F) else MaterialTheme.colorScheme.surface,
            labelColor = if (selected) Color(0xFFFFCDD2) else MaterialTheme.colorScheme.onSurface,
            selectedContainerColor = Color(0xFFD32F2F),
            selectedLabelColor = Color(0xFFFFCDD2),
            selectedTrailingIconColor = Color(0xFFFFCDD2)
        ),
        modifier = Modifier
            .scale(scale)
            .padding(end = 8.dp),
        interactionSource = interactionSource
    )
}
