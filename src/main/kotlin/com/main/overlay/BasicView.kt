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

    init {
        primaryStage.isAlwaysOnTop = true
        primaryStage.opacity = 0.4
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

                val property = SimpleObjectProperty<ObservableList<XYChart.Data<String, Number>>>()
                property.value = observableList()
                val x = XYChart.Data<String, Number>("", 2)


                val series = series("Overview") {
                    dataProperty().bind(property)
                }

                listOfSeries.add(series)

                viewModel.firstSeriesDataList.addListener(
                        ListChangeListener {
                            val list = it.list
                            for (value in list) {
                                val newPoint = XYChart.Data<String, Number>(value.first, value.second)
                                property.value.add(newPoint)
                            }


                        }
                )


                data = listOfSeries

            }

        }

        style {
            fill = Color.TRANSPARENT
        }
    }
}