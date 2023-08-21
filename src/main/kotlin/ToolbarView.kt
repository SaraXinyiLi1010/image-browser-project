import javafx.scene.control.Button
import javafx.scene.control.ToolBar
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.FileChooser
import javafx.stage.Stage
import java.io.File

class ToolbarView(stage: Stage) : IView, ToolBar() {
    override var imagesCount:Int = 0
    override var selectedImageName:String? = null
    var model:Model? = null
    override var tileView:Boolean = false
    private val addImageButton = Button("Add Image")
    private val deleteImageButton = Button("Del Image")
    private val rotateLeftButton = Button("Rotate Left")
    private val rotateRightButton = Button("Rotate Right")
    private val zoomInButton = Button("Zoom in")
    private val zoomOutButton = Button("Zoom out")
    private val resetButton = Button("Reset")
    private val cascadeButton = Button("Cascade")
    private val tileButton = Button("Tile")

    init {
        addImageButton.graphic = ImageView(Image("icons8-add-image-24.png"))
        deleteImageButton.graphic = ImageView(Image("icons8-remove-image-24.png"))
        rotateLeftButton.graphic = ImageView(Image("icons8-rotate-left-24.png"))
        rotateRightButton.graphic = ImageView(Image("icons8-rotate-right-24.png"))
        zoomInButton.graphic = ImageView(Image("icons8-zoom-in-24.png"))
        zoomOutButton.graphic = ImageView(Image("icons8-zoom-out-24.png"))
        resetButton.graphic = ImageView(Image("icons8-shutdown-24.png"))
        cascadeButton.graphic = ImageView(Image("cascade-rectangular-rellena.png"))
        tileButton.graphic = ImageView(Image("icons8-medium-icons-24.png"))
        addImageButton.setOnAction {
            val fileChooser = FileChooser()
            fileChooser.title = "Pick an image"
            val file:File? = fileChooser.showOpenDialog(stage)
            // if empty?
            if (file === null) {
                println("Image selection cancelled")
            } else {
                var extension = file.extension
                if (extension == "jpg" || extension == "png" || extension == "bmp") {
                    println("An image file")
                    println(file.name)
                    model!!.files.add(file)
                    imagesCount++
                    model?.addImage(file)
                    println("the file is"+ file.absolutePath)
                }
                else {
                    println("The file you selected is not an image file")
                }

            }
        }

        deleteImageButton.setOnAction {
            model!!.deleteSelected()
            deleteAnImage()
        }

        zoomInButton.setOnAction {
            model!!.zoomInSelected()
        }
        zoomOutButton.setOnAction {
            model!!.zoomOutSelected()
        }

        rotateLeftButton.setOnAction {
            model!!.rotateLeftSelected()
        }

        rotateRightButton.setOnAction {
            model!!.rotateRightSelected()
        }

        resetButton.setOnAction {
            model!!.resetImageSelected()
        }

        tileButton.setOnAction {
            model!!.updateTileView(true)
            rotateLeftButton.isDisable = true
            rotateRightButton.isDisable = true
            zoomInButton.isDisable = true
            zoomOutButton.isDisable = true
            resetButton.isDisable = true
        }

        cascadeButton.setOnAction {
            if  (tileView) {
                model!!.updateTileView(false)
                if (selectedImageName != null) {
                    rotateLeftButton.isDisable = false
                    rotateRightButton.isDisable = false
                    zoomInButton.isDisable = false
                    zoomOutButton.isDisable = false
                    resetButton.isDisable = false
                }
            }
        }

        if (selectedImageName == null) {
            rotateLeftButton.isDisable = true
            rotateRightButton.isDisable = true
            zoomInButton.isDisable = true
            zoomOutButton.isDisable = true
            resetButton.isDisable = true
        }

        // add buttons to toolbar
        this.items.add(addImageButton)
        this.items.add(deleteImageButton)
        this.items.add(rotateLeftButton)
        this.items.add(rotateRightButton)
        this.items.add(zoomInButton)
        this.items.add(zoomOutButton)
        this.items.add(resetButton)
        this.items.add(cascadeButton)
        this.items.add(tileButton)

    }

    override fun deleteAnImage() {
        if (selectedImageName != null) {
            imagesCount--
            selectedImageName = null
        }
    }

    override fun returnToCascade() {
        //do nothing
    }

    override fun rotateLeftAnImage() {
        // do nothing
    }

    override fun rotateRightAnImage() {
        // do nothing
    }

    override fun addImage(file:File) {
        //do nothing
    }

    override fun zoomInAnImage() {
        //do nothing
    }

    override fun zoomOutAnImage() {
        // do nothing
    }

    override fun resetAnImage() {
        //  do nothing
    }

    override fun updateSelectedImageName(name: String) {
        selectedImageName = name
        if (!tileView) {
            rotateLeftButton.isDisable = false
            rotateRightButton.isDisable = false
            zoomInButton.isDisable = false
            zoomOutButton.isDisable = false
            resetButton.isDisable = false
        }
    }
    override fun addModel(myModel:Model) {
        model = myModel
    }

    override fun changeToTileViewMode() {
    }

    override fun update() {
    }

    override fun deselectImage() {
        selectedImageName = null
        rotateLeftButton.isDisable = true
        rotateRightButton.isDisable = true
        zoomInButton.isDisable = true
        zoomOutButton.isDisable = true
        resetButton.isDisable = true
    }
}