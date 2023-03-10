package ru.nsu.ccfit.kivis

import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import ru.nsu.ccfit.kivis.component.Menu
import java.awt.Dimension

class SizeWindows {
    companion object {
        fun height(): Int = 480
        fun width(): Int = 640
    }
}

fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "Kivis",
        icon = painterResource("kivi_logo.png"),
        state = WindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            width = SizeWindows.width().dp,
            height = SizeWindows.height().dp
        )
    ) {
        window.minimumSize = Dimension(SizeWindows.width(), SizeWindows.height())
        Menu().render(this)
        MainWindow()
    }
}