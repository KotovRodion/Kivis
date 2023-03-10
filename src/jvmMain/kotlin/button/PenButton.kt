package ru.nsu.ccfit.kivis.button

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.mouseClickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Edit3
import ru.nsu.ccfit.kivis.dialog.PenDialog
import ru.nsu.ccfit.kivis.tool.PenTool
import ru.nsu.ccfit.kivis.tool.Tool

class PenButton(private val currentTool: MutableState<Tool>) : Button() {
    private val name: String = "Линия"
    private val viewDialog = mutableStateOf(false)

    override fun handleClick() {
        changeTool()
    }

    private fun changeTool(){
        currentTool.value = PenTool
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun render() {
        val dialog = remember { viewDialog }
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
            modifier = Modifier.padding(start = 40.dp).mouseClickable {  viewDialog.value = true  },
            delayMillis = 600,
            tooltipPlacement = TooltipPlacement.CursorPoint(
                alignment = Alignment.BottomEnd
            )
        ) {
            IconButton(onClick = { handleClick() }) {
                Icon(FeatherIcons.Edit3, contentDescription = "Localized description")
            }
            if (dialog.value) {
                PenDialog({ changeTool() }, { dialog.value=false })
            }
        }
    }
}