package ru.nsu.ccfit.kivis.button

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Trash2
import ru.nsu.ccfit.kivis.tool.Tool
import ru.nsu.ccfit.kivis.tool.TrashTool

class TrashButton(private val currentTool: MutableState<Tool>) : Button() {
    private val name: String = "Стереть"


    override fun handleClick() {
        currentTool.value = TrashTool
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun render() {
        TooltipArea(
            tooltip = {
                Surface(
                    modifier = Modifier.shadow(4.dp),
                    color = Color(0, 0, 0, 255),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = name,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            },
            modifier = Modifier.padding(start = 40.dp),
            delayMillis = 600,
            tooltipPlacement = TooltipPlacement.CursorPoint(
                alignment = Alignment.BottomEnd
            )
        ) {
            IconButton(onClick = { handleClick() }) {
                Icon(FeatherIcons.Trash2, contentDescription = "Localized description")
            }
        }

    }
}