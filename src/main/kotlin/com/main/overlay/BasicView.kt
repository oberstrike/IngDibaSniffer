package com.main.overlay

import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.paint.Color
import tornadofx.*

class BasicView : View() {

    private val viewModel: BasicViewModel by inject()

    companion object {

    }

    init {
        primaryStage.isAlwaysOnTop = true
        primaryStage.opacity = 0.8
    }

    override fun onDock() {
        currentWindow?.setOnCloseRequest {
            viewModel.onStop()
        }
        super.onDock()
    }

    override val root = gridpane {
        row {
            label(viewModel.timeProperty.stringBinding {
                if (it != null) {
                    var minutes = it.toInt() / 60
                    val seconds = it.toInt() - (minutes * 60)
                    val hour = minutes / 60
                    if (hour > 0) {
                        minutes -= hour * 60
                    }

                    return@stringBinding "Hour: ${if (hour < 10) "0$hour" else hour}," +
                            "Minute: ${if (minutes < 10) "0$minutes" else minutes}," +
                            " second: ${if (seconds < 10) "0$seconds" else seconds}"
                }

                return@stringBinding ""
            })
        }

        row {
            linechart("Title", CategoryAxis(), NumberAxis()) {

                val listOfSeries = observableList<XYChart.Series<String, Number>>();

                val firstSeriesProperty = SimpleObjectProperty<ObservableList<XYChart.Data<String, Number>>>()
                firstSeriesProperty.value = observableList()

                val secondSeriesProperty = SimpleObjectProperty<ObservableList<XYChart.Data<String, Number>>>()
                secondSeriesProperty.value = observableList()

                val firstSeries = series("Wood") {
                    dataProperty().bind(firstSeriesProperty)
                }

                val secondSeries = series("Stone") {
                    dataProperty().bind(secondSeriesProperty)
                }

                listOfSeries.add(firstSeries)
                listOfSeries.add(secondSeries)

                viewModel.firstSeriesDataList.addListener(
                        ListChangeListener {
                            val list = it.list
                            for (value in list) {
                                val newPoint = XYChart.Data<String, Number>(value.first, value.second)
                                firstSeriesProperty.value.add(newPoint)
                            }
                            if (list.isEmpty()) {
                                firstSeriesProperty.value.clear()
                            }
                        }
                )

                viewModel.secondSeriesDataList.addListener(
                        ListChangeListener {
                            val list = it.list
                            for (value in list) {
                                val newPoint = XYChart.Data<String, Number>(value.first, value.second)
                                secondSeriesProperty.value.add(newPoint)
                            }
                            if (list.isEmpty()) {
                                secondSeriesProperty.value.clear()
                            }
                        }
                )


                row {
                    label("Hot Reload!")

                }

                data = listOfSeries

            }

        }

        style {
            fill = Color.TRANSPARENT
        }
    }
}