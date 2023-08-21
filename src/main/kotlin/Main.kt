import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.ScrollPane
import javafx.scene.layout.VBox
import javafx.stage.Stage

class Main : Application() {
    override fun start(stage: Stage) {
        val model = Model()

        // our layout is the root of the scene graph
        val root = VBox()

        // views are the children of the top-level layout
        val toolbar = ToolbarView(stage)
        val canvas = CanvasView()
        canvas.hbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
        canvas.vbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
        val status = StatusView()


        canvas.prefHeight = 990.0
        toolbar.addModel(model)
        canvas.addModel(model)
        status.addModel(model)
        // register views with the model
        model.addView(toolbar)
        model.addView(canvas)
        model.addView(status)

        // setup and display
        root.children.addAll(toolbar, canvas, status) // gridView
        stage.scene = Scene(root, 1000.0, 750.0)
        stage.minHeight = 750.0
        stage.minWidth = 1000.0
        stage.isResizable = true
        stage.title = "Light Box 2021 Xinyi(Sara) Li x784li"
//        canvas.minWidthProperty().bind(stage.widthProperty().subtract(50))
//        canvas.minHeightProperty().bind(stage.heightProperty().subtract(50))
        stage.show()
    }
}