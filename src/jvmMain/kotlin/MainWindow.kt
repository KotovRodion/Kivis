package ru.nsu.ccfit.kivis

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.unit.dp
import org.jetbrains.skiko.toBufferedImage
import ru.nsu.ccfit.kivis.component.Menu
import ru.nsu.ccfit.kivis.component.PaintCanvas
import ru.nsu.ccfit.kivis.component.ToolBar
import ru.nsu.ccfit.kivis.dialog.*
import ru.nsu.ccfit.kivis.tool.*
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

class MainWindowController {
    companion object {
        val currentTool = mutableStateOf<Tool>(PenTool())

        val currentPenTool = mutableStateOf(PenTool())
        val currentPolygonTool = mutableStateOf(PolygonTool())
        val currentFillTool = mutableStateOf(FillTool())

        val image = mutableStateOf(ImageBitmap(10, 10, hasAlpha = false))
        var isPaint = mutableStateOf(false)

        val toolBar = ToolBar(currentTool, currentPenTool, currentFillTool, currentPolygonTool)
        val canvas = PaintCanvas(image, isPaint)
    }
}

var previousClick: Pair<Offset, Offset> = Offset(0f, 0f) to Offset(0f, 0f)

@Composable
@Preview
fun MainWindow() {

    val s = MainWindowController.currentTool.value
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val dialogAbout = remember { Menu.Controller.about }
        val saveAction = remember { Menu.Controller.save }
        val dialogManual = remember { Menu.Controller.instruction }
        val dialogTool = remember { Menu.Controller.tool }

        val click = remember { MainWindowController.canvas.offsetClick }
        Box(Modifier.fillMaxWidth()) {
            MainWindowController.toolBar.render()
        }
        Box(Modifier.fillMaxSize().background(Color.Gray).padding(10.dp)) {

            if (click.value != previousClick)
                s.draw(MainWindowController.canvas)
            previousClick = click.value

            MainWindowController.canvas.render()
        }
        when (dialogTool.value) {
            PenTool.name -> {
                PenDialog(MainWindowController.currentPenTool,
                    {
                        MainWindowController.currentTool.value = MainWindowController.currentPenTool.value
                    }) { dialogTool.value = "TOOL" }
            }

            PolygonTool.name -> {
                PolygonDialog(MainWindowController.currentPolygonTool,
                    { MainWindowController.currentTool.value = MainWindowController.currentPolygonTool.value },
                    { dialogTool.value = "TODO" })
            }


            FillTool.name -> {
                FillDialog(MainWindowController.currentFillTool,
                    { MainWindowController.currentTool.value = MainWindowController.currentFillTool.value },
                    { dialogTool.value = "TODO" })
            }

            TrashTool.name -> {
                MainWindowController.currentTool.value = TrashTool()
            }
        }

        if (saveAction.value) {
            FileDialog {
                if (it != null) {
                    val output = File(it.plus(".png"))
                    output.createNewFile()

                    try {
                        ImageIO.write(MainWindowController.image.value.asSkiaBitmap().toBufferedImage(), "PNG", output)
                    } catch (e: IOException) {
                        println(e.message)
                    }
                }
                saveAction.value = false
            }
        }

        if (dialogAbout.value) {
            AboutDialog { dialogAbout.value = false }
        }

        if (dialogManual.value) {
            ManualDialog { dialogManual.value = false }
        }
    }
}