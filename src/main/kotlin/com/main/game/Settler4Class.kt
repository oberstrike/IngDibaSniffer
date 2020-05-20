package com.main.game

import com.main.state.GameState
import com.main.state.PlayerSate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jire.arrowhead.Process
import org.jire.arrowhead.processByName
import org.jire.arrowhead.windows.WindowsModule
import java.util.logging.Logger

class Settler4Class(private val onTimeChangedListener: OnTimeChangedListener,
                    private val onGameIsOverListener: OnGameIsOverListener
) {

    private val logger = Logger.getLogger(Settler4Class::class::simpleName.name)

    companion object {
        const val processName = "S4_Main.exe"
    }

    private var settlerExe: Process? = null

    private var isRunning = false

    init {
        settlerExe = processByName(processName)
        isRunning = settlerExe != null
    }

    suspend fun readProcess() {
        with(settlerExe) {
            if (settlerExe != null) {
                val process = this!!
                process.loadModules()
                val module = process.modules["S4_Main.exe"]

                if (module == null) {
                    settlerExe = null
                    println("Game is closed")
                    return@with
                }

                val baseAddress = module.address

                val timeRaw = process.int(baseAddress + GameState.time.offset)
                val oldTime = GameState.time

                if (oldTime.value != 0 && timeRaw == 0) {
                    onGameIsOverListener.onGameIsOver()
                    GameState.time.value = 0
                    GameState.isInGame = false
                    return
                }

                GameState.time.value = (timeRaw / 14.1).toInt()
                GameState.isInGame = timeRaw != 0


                with(PlayerSate.ResourceState) {
                    val newWoodValue = process.int(baseAddress + wood.offset)

                    if (wood.value != 0) {
                        val newWoodDiff = wood.value - newWoodValue
                        woodCounter += newWoodDiff
                    } else {
                        woodCounter = 0
                    }

                    wood.value = newWoodValue

                    val newStoneValue = process.int(baseAddress + stone.offset)
                    val newStoneDiff = stone.value - newStoneValue


                }

                onTimeChangedListener.onTimeChanged(GameState.time.value)
            } else {
                settlerExe = processByName(processName)
            }
        }
    }
}

interface OnTimeChangedListener {
    suspend fun onTimeChanged(time: Int)
}

interface OnGameIsOverListener {
    suspend fun onGameIsOver()
}

