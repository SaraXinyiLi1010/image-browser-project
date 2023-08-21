import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import java.io.File

class StatusView() : IView, HBox() {
    override var imagesCount = 0
    override var selectedImageName:String? = null
    override var tileView:Boolean = false
    private var model: Model? = null
    private var imageCountMessage = "$imagesCount images loaded"
    private var imageCountLabel = Label(imageCountMessage)
    private var imageSelectedMessage: String =""
    private var imageSelectedLabel = Label(imageSelectedMessage)

    init {
        this.height = 10.0
        imageCountLabel.alignment = Pos.CENTER_LEFT
        imageCountLabel.padding = Insets(5.0)
        imageCountLabel.prefWidth = 500.0
        imageSelectedLabel.alignment = Pos.CENTER_RIGHT
        imageSelectedLabel.padding = Insets(5.0)
        imageSelectedLabel.prefWidth = 500.0
        this.children.add(imageCountLabel)
        this.children.add(imageSelectedLabel)
    }

    override fun addImage(file: File) {
        imagesCount++
        println("The image count in status is $imagesCount")
        imageCountMessage = "$imagesCount images loaded"
        imageCountLabel.text = imageCountMessage
    }
    override fun addModel(myModel: Model) {
        model = myModel
    }

    override fun updateSelectedImageName(name: String) {
        println("name updated in status")
        selectedImageName = name
        imageSelectedMessage = "Selected image: $selectedImageName"
        imageSelectedLabel.text = imageSelectedMessage
    }

    override fun deleteAnImage() {
        if (selectedImageName != null) {
            imageSelectedMessage = ""
            imagesCount--
            selectedImageName = null
            imageCountMessage = "$imagesCount images loaded"
            imageCountLabel.text = imageCountMessage
            imageSelectedLabel.text = imageSelectedMessage
        }
    }


    override fun zoomInAnImage() {
        //  do nothing
    }

    override fun zoomOutAnImage() {
        //  do nothing
    }

    override fun rotateLeftAnImage() {
        //  do nothing
    }

    override fun rotateRightAnImage() {
        //  do nothing
    }

    override fun resetAnImage() {
        // do nothing
    }

    override fun returnToCascade() {
        // do nothing
    }

    override fun changeToTileViewMode() {
        // do nothing
    }
    override fun deselectImage() {
        imageSelectedMessage = ""
        selectedImageName = null
        imageSelectedLabel.text = imageSelectedMessage
    }
    override fun update() {
        // react to updates from model
        // how do we get data from the model? do we need it?
    }
}