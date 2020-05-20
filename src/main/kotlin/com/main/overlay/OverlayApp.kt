package com.main.overlay

import com.main.game.MainContext
import tornadofx.App
import tornadofx.UIComponent
import kotlin.reflect.KClass

class OverlayApp : App() {

    override val primaryView: KClass<out UIComponent>
        get() = BasicView::class
    
}





