import javafx.scene.paint.Color
import tornadofx.*

fun main(args: Array<String>) {
    launch<MyApp>()
}


class MyApp : App(MyView::class)


class MyView : View() {
    init {
        primaryStage.isAlwaysOnTop = true
    }

    override val root = vbox {
        style {
            backgroundColor += Color.TRANSPARENT
        }
    }
}