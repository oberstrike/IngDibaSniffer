package com.main.overlay

import com.main.game.OnGameIsOverListener
import com.main.game.OnTimeChangedListener
import com.main.game.Settler4Class
import com.main.state.GameState
import com.main.state.PlayerSate
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import kotlinx.coroutines.*
import kotlinx.coroutines.javafx.JavaFx
import tornadofx.ViewModel
import tornadofx.alert
import tornadofx.observableList

class BasicViewModel : ViewModel(), OnTimeChangedListener, OnGameIsOverListener {

    val timeProperty = SimpleStringProperty("0")

    val firstSeriesDataList = observableList<Pair<String, Number>>()

    val wordCountList = listOf<Pair<String, Number>>()


    private val settler4Class = Settler4Class(this, this)

    private val backgroundJob: Job = GlobalScope.launch(Dispatchers.IO) {
        while (isActive) {
            delay(500)
            settler4Class.readProcess()
        }
    }

    override suspend fun onTimeChanged(time: Int) {
        withContext(Dispatchers.JavaFx) {
            timeProperty.value = time.toString()
            if (GameState.isInGame) {
                if (time % 10 == 0) {
                    firstSeriesDataList.add(time.toString() to PlayerSate.ResourceState.woodCounter)
                }
            } else {
                firstSeriesDataList.clear()
            }
        }
    }

    override suspend fun onGameIsOver() {
        withContext(Dispatchers.JavaFx) {
            alert(Alert.AlertType.CONFIRMATION, "Game is Over")
            firstSeriesDataList.clear()
        }
    }

    fun onStop() {
        backgroundJob.cancel()
    }
}