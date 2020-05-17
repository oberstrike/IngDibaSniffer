package com.main.game

import com.main.state.GameState
import com.main.state.PlayerSate
import com.main.util.entity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jire.arrowhead.Process
import org.jire.arrowhead.processByName
import org.jire.arrowhead.windows.WindowsModule
import java.util.logging.Logger

class Settler4Class {

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

    fun readProcess() {
        with(settlerExe) {
            if (settlerExe != null) {
                val process = this!!
                process.loadModules()
                val baseAddress = (process.modules["S4_Main.exe"] as WindowsModule).address

                runBlocking {
                    withContext(Dispatchers.JavaFx){
                        with(GameState) {
                            time.setValue(process.int(baseAddress + time.offset))
                        }
                    }
                }





                with(PlayerSate.Settler) {
                    sword.setValue(process.entity(sword))
                    archery.setValue(process.entity(archery))
                }

                with(PlayerSate.Building) {
                    wood.setValue(process.entity(wood))
                    stone.setValue(process.entity(stone))
                    sawmill.setValue(process.entity(sawmill))
                }
            } else {
                settlerExe = processByName(processName)
            }
        }
    }
}

