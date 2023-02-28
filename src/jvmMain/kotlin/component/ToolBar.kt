package ru.nsu.ccfit.kivis.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import ru.nsu.ccfit.kivis.button.FillButton
import ru.nsu.ccfit.kivis.button.TrashButton
import ru.nsu.ccfit.kivis.button.PenButton
import ru.nsu.ccfit.kivis.button.PolygonButton
import ru.nsu.ccfit.kivis.tool.*


class ToolBar(
   private val currentTool: MutableState<Tool>,
   private val currentPenTool: MutableState<PenTool>,
   private val currentFillTool: MutableState<FillTool>,
   private val currentPolygonTool: MutableState<PolygonTool>
) : Renderable {

    @Composable
    override fun render() {
        TopAppBar(title = {
            Row {
                when (currentTool.value) {
                    is PenTool -> {
                        Text(PenTool.name)
                    }
                    is PolygonTool -> {
                        Text(PolygonTool.name)
                    }
                    is FillTool -> {
                        Text(FillTool.name)
                    }
                    is TrashTool -> {
                        Text(TrashTool.name)
                    }
                }
            }
        }, modifier = Modifier.fillMaxWidth(),
            actions = {
                Row {

                    // Box(modifier = if (currentTool.value is PenTool) Modifier.background(Color.Blue) else Modifier){
                    PenButton(currentTool, currentPenTool).render()
                    //}
                    PolygonButton(currentTool, currentPolygonTool).render()
                    FillButton(currentTool, currentFillTool).render()
                    TrashButton(currentTool).render()
                }
            })
    }
}