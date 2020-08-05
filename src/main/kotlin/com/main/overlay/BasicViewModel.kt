package com.main.overlay

import com.main.game.Settler4Class
import com.main.game.SettlerListener
import com.main.state.GameState
import com.main.state.PlayerSate
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import kotlinx.coroutines.*
import kotlinx.coroutines.javafx.JavaFx
import tornadofx.ViewModel
import tornadofx.alert
import tornadofx.observableList

class BasicViewModel : ViewModel(), SettlerListener {

    val timeProperty = SimpleStringProperty("0")

    val firstSeriesDataList = observableList<Pair<String, Number>>()

    val secondSeriesDataList = observableList<Pair<String, Number>>()

    private val settler4Class = Settler4Class(this)

    private val backgroundJob: Job = GlobalScope.launch(Dispatchers.IO) {
        while (isActive) {
            delay(250)
            settler4Class.readProcess()
        }
    }

    override suspend fun onTimeChanged(time: Int) {
        withContext(Dispatchers.JavaFx) {
            timeProperty.value = time.toString()
            if (GameState.isInGame) {
                if (time % 10 == 0) {
                    //Alle 10 Sekunden
                    firstSeriesDataList.add(time.toString() to PlayerSate.ResourceState.usedPlanksCounter)
                    secondSeriesDataList.add(time.toString() to PlayerSate.ResourceState.usedStoneCounter)
                }
            } else {
                firstSeriesDataList.clear()
            }
        }
    }

    override suspend fun onGameIsOver() {
        withContext(Dispatchers.JavaFx) {
            val alert = Alert(Alert.AlertType.CONFIRMATION)
            alert.headerText = "You want to save the game?"

            val result = alert.showAndWait().orElse(null)
            if (result != null) {
                if (result == ButtonType.OK) {

                }
            }

            firstSeriesDataList.clear()
        }
    }

    override suspend fun onGameIsStarted() {
        println("Game is started")
    }

    fun onStop() {
        backgroundJob.cancel()
    }
}