package com.main.game

import com.main.state.GameState
import com.main.state.PlayerSate
import org.jire.arrowhead.Process
import org.jire.arrowhead.get
import org.jire.arrowhead.processByName
import java.util.logging.Logger

class Settler4Class(private val settlerListener: SettlerListener) {

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
                    settlerListener.onGameIsOver()
                    GameState.time.value = 0
                    GameState.isInGame = false
                    return
                }

                GameState.time.value = (timeRaw / 14.1).toInt()
                val isInGame = (timeRaw != 0)

                val newGameIsStarted = GameState.isInGame != isInGame
                GameState.isInGame = isInGame

                if (newGameIsStarted) settlerListener.onGameIsStarted()

                with(PlayerSate.ResourceState) {
                    extractResource(process, baseAddress, newGameIsStarted)
                }

                with(PlayerSate.Settler) {
                    extractResource(process, baseAddress, newGameIsStarted)
                }

                settlerListener.onTimeChanged(GameState.time.value)
            } else {
                settlerExe = processByName(processName)
            }
        }
    }

    private fun PlayerSate.Settler.extractResource(process: Process, baseAddress: Long, newGameIsStarted: Boolean) {
        val newFreeSettlerValue = process.int(baseAddress + freeSettler.offset)
        val newWorkerValue = process.int(baseAddress + worker.offset)
        val newPlanerValue = process.int(baseAddress + planer.offset)
        val newNumberOfFreeBeds = process.int(baseAddress + numberOfFreeBeds.offset)

        freeSettler.value = newFreeSettlerValue
        worker.value = newWorkerValue
        planer.value = newPlanerValue
        numberOfFreeBeds.value = newNumberOfFreeBeds

    }


    private fun PlayerSate.ResourceState.extractResource(process: Process, baseAddress: Long, newGameIsStarted: Boolean) {
        val newPlanksValue = process.int(baseAddress + planks.offset)
        val newStoneValue = process.int(baseAddress + stone.offset)
        val newRawWoodValue = process.int(baseAddress + rawWood.offset)
        val newRawWoodAllTimeValue = process.int(baseAddress + rawWoodAllTime.offset)

        if (newGameIsStarted) {
            usedPlanksCounter = 0
            usedStoneCounter = 0
            usedRawWood = 0
        } else {
            usedPlanksCounter += planks.value - newPlanksValue
            usedStoneCounter += stone.value - newStoneValue
            usedRawWood += rawWood.value - newRawWoodValue
        }


        planks.value = newPlanksValue
        stone.value = newStoneValue
        rawWoodAllTime.value = newRawWoodAllTimeValue
        rawWood.value = newRawWoodValue
    }
}

interface OnTimeChangedListener {
    suspend fun onTimeChanged(time: Int)
}

interface OnGameIsOverListener {
    suspend fun onGameIsOver()
}

interface OnGameIsStartedListener {
    suspend fun onGameIsStarted()
}

interface SettlerListener : OnTimeChangedListener, OnGameIsOverListener, OnGameIsStartedListener