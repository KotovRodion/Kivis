package ru.nsu.ccfit.kivis.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.*
import ru.nsu.ccfit.kivis.tool.PenTool


class PaintCanvas(
    val image: MutableState<ImageBitmap>,
    private val isPaint: MutableState<Boolean>
) : Renderable {
    val offsetClick = mutableStateOf(Offset(0f,0f) to  Offset(0f,0f))
    var offsetPress = (Offset(0F, 0F))
    var offsetRelease = (Offset(0F, 0F))

    private fun initCanvas(weight: Int, height: Int): ImageBitmap {
        val preBitmap = ImageBitmap(
            width = weight,
            height = height,
            config = ImageBitmapConfig.Rgb565,
            hasAlpha = false
        )
        val canvas = Canvas(image = preBitmap)
        val paint = Paint()
        paint.color = Color.White
        paint.style = PaintingStyle.Fill
        canvas.drawRect(Rect(Offset(0F, 0F), Offset(weight.toFloat(), height.toFloat())), paint)
        return preBitmap
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun render() {
        var positionRelease: Offset
        var positionPress: Offset
        Canvas(modifier = Modifier.fillMaxSize()
            .onPointerEvent(PointerEventType.Release) {
                positionRelease = it.changes.first().position
                offsetRelease = positionRelease
                offsetClick.value = offsetRelease to offsetPress
                //PenTool().draw(this@PaintCanvas)
            }.onPointerEvent(PointerEventType.Press) {
                positionPress = it.changes.first().position
                offsetPress = positionPress
            }) {
            if (!isPaint.value) {
                isPaint.value = true
                image.value = initCanvas(size.width.toInt(), size.height.toInt())
            }
            drawImage(image = image.value)
        }
    }
}