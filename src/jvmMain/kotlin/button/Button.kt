package ru.nsu.ccfit.kivis.button

import ru.nsu.ccfit.kivis.Renderable


abstract class Button protected constructor() : Renderable {
    abstract fun handleClick();
}