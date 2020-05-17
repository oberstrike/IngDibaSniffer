package com.main.overlay

import com.main.game.MainContext
import com.main.state.GameState
import com.main.state.PlayerSate
import com.main.util.entityFragment
import javafx.beans.binding.StringBinding
import javafx.scene.paint.Color
import tornadofx.*
import kotlin.reflect.KClass

class OverlayApp : App() {

    override val primaryView: KClass<out UIComponent>
        get() = BasicView::class

    override fun stop() {
        MainContext.mainJob.cancel()
        super.stop()
    }
}

interface OnStopListener {
    fun onStop()
}

class BasicView : View() {
    init {
        primaryStage.isAlwaysOnTop = true
        primaryStage.opacity = 0.4
    }

    override fun onDock() {
        currentWindow?.setOnCloseRequest {
            print("Hide")
        }
        super.onDock()
    }


    override val root = gridpane {

        row {
            add(entityFragment("time", GameState.time))
        }

        row {
            add(entityFragment("sword", PlayerSate.Settler.sword))
        }

        style {
            fill = Color.TRANSPARENT
        }
    }
}

class EntityFragment : Fragment() {

    private val property: StringBinding by param()

    private val propertyName: String by param()

    init {

    }

    override val root = gridpane {
        row {
            add(label(propertyName))

            add(label(property) {
                style {
                    backgroundColor += Color.TRANSPARENT
                    fontSize = 22.px
                }
            })
        }
    }
}
