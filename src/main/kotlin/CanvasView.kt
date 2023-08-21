import javafx.scene.Group
import javafx.scene.control.ScrollPane
import javafx.scene.effect.DropShadow
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.paint.Color;
import java.io.File
import java.io.FileInputStream
import kotlin.math.pow


class CanvasView: IView, ScrollPane() {
    override var imagesCount:Int = 0
    override var selectedImageName:String? = null
    override var tileView:Boolean = false
    private var selectedImageView:ImageView? = null
    private var model: Model? = null
    private val group = Group()
    private val rootGroup = Pane()
    private var imageViews: ArrayList<ImageView> = arrayListOf()
    private var startX = -1.0
    private var startY = -1.0
    private enum class STATE { NONE, DRAG }
    private var state = STATE.NONE
    private var scrollPaneWidth  = 980.0
    private var scrollPaneHeight = 720.0

    init {
//        this.prefHeight = SCREEN_HEIGHT
//        this.prefWidth = SCREEN_WIDTH
        // How do you check if this is the background being clicked but not the image
        this.setOnMouseClicked {
            println("Image Deselected")
            model!!.deselectImage()
        }
//        rootGroup.minWidthProperty().bind(this.widthProperty().subtract(10))
//        rootGroup.minHeightProperty().bind(this.heightProperty().subtract(10))
        group.children.add(rootGroup)
        this.widthProperty().addListener { obs, oldVal, newVal ->
            println(this.width)
            scrollPaneWidth = this.width - 20.0
            if(tileView) {
                changeToTileViewMode()
            }
        }

        this.heightProperty().addListener {obs, oldVal, newVal ->
            scrollPaneHeight = this.height - 20.0

        }
    }

    override fun update() {
    }

    override fun addImage(file: File) {
        println("called canvas view with file"+file.path)
        imagesCount++
        var fileImage = Image(FileInputStream(file.path))
        var newImageView = ImageView(fileImage)
        newImageView.fitWidth = 320.0
        newImageView.isPreserveRatio = true
        newImageView.userData = file.name
        imageViews.add(newImageView)
        newImageView.translateX = (0..200).random() * 1.0
        newImageView.translateY = (0..200).random() * 1.0
        println("translation: "+ imagesCount * 100.0)


        newImageView.setOnMouseClicked { event ->
            event.consume()
            println(newImageView.userData.toString()+"is selected")
            if (selectedImageView != null) {
                selectedImageView!!.effect = null
            }
            selectedImageView = newImageView
            newImageView.effect = DropShadow(20.0, Color.BLACK)
            selectedImageName = newImageView.userData.toString()
            rootGroup.children.remove(newImageView)
            rootGroup.children.add(newImageView)
            model!!.updateSelectedImage(newImageView.userData.toString())
        }

        newImageView.setOnMousePressed { event ->
            if (!tileView) {
                startX = event.sceneX
                startY = event.sceneY
                state = STATE.DRAG
            }
        }

        newImageView.setOnMouseDragged { event ->
            if  (!tileView) {
                if (state == STATE.DRAG) {
                    val dx = event.sceneX - startX
                    val dy = event.sceneY - startY

                    if (newImageView.boundsInParent.minX + dx >= 0.0 && newImageView.boundsInParent.maxX + dx <= scrollPaneWidth) {
                        newImageView.translateX += dx
                        startX = event.sceneX
                    }

                    if (newImageView.boundsInParent.minY + dy >= 0.0 && newImageView.boundsInParent.maxY + dy <= scrollPaneHeight) {
                        newImageView.translateY += dy
                        startY = event.sceneY
                    }
                }
            }
        }
        newImageView.setOnMouseReleased {
            if (!tileView) {
                state = STATE.NONE
            }
        }

        newImageView.setOnMouseExited {
            if (!tileView) {
                state = STATE.NONE
            }
        }

        rootGroup.children.add(newImageView)
        this.content = group
        if (tileView) {
            changeToTileViewMode()
        }
    }

    override fun updateSelectedImageName(name: String) {
        //nothing
    }

    override fun zoomInAnImage() {
        if(selectedImageView != null) {
            var translatedX:Double = selectedImageView!!.translateX
            var translatedY:Double = selectedImageView!!.translateY
            selectedImageView!!.translateX = 0.0
            selectedImageView!!.translateY = 0.0
            if (selectedImageView!!.scaleX <= 1.25.pow(3.0)) {
                selectedImageView!!.scaleX = selectedImageView!!.scaleX * 1.25
                selectedImageView!!.scaleY = selectedImageView!!.scaleY * 1.25
            }
            selectedImageView!!.translateX = translatedX
            selectedImageView!!.translateY = translatedY
        }
    }
    override fun deleteAnImage() {
        println("Entered delete an message in canvas")
        if (selectedImageView != null) {
            imagesCount--
            rootGroup.children.remove(selectedImageView)
            imageViews.remove(selectedImageView)
            selectedImageView = null
            selectedImageName = null
            println("Image views size change " + imageViews.size)
        }
        if (tileView) {
            changeToTileViewMode()
        }
    }

    override fun zoomOutAnImage() {
        if  (selectedImageView != null) {
            var translatedX:Double = selectedImageView!!.translateX
            var translatedY:Double = selectedImageView!!.translateY
            selectedImageView!!.translateX = 0.0
            selectedImageView!!.translateY = 0.0
            if (selectedImageView!!.scaleX >= 0.75.pow(3.0)) {
                selectedImageView!!.scaleX = selectedImageView!!.scaleX * 0.75
                selectedImageView!!.scaleY = selectedImageView!!.scaleY * 0.75
            }
            selectedImageView!!.translateX = translatedX
            selectedImageView!!.translateY = translatedY
        }
    }

    override fun rotateLeftAnImage() {
        if  (selectedImageView != null) {
            var translatedX:Double = selectedImageView!!.translateX
            var translatedY:Double = selectedImageView!!.translateY
            selectedImageView!!.translateX = 0.0
            selectedImageView!!.translateY = 0.0
            selectedImageView!!.rotate = selectedImageView!!.rotate - 10.0
            selectedImageView!!.translateX = translatedX
            selectedImageView!!.translateY = translatedY
        }
    }

    override fun rotateRightAnImage() {
        if  (selectedImageView != null) {
            var translatedX:Double = selectedImageView!!.translateX
            var translatedY:Double = selectedImageView!!.translateY
            selectedImageView!!.translateX = 0.0
            selectedImageView!!.translateY = 0.0
            selectedImageView!!.rotate = selectedImageView!!.rotate + 10.0
            selectedImageView!!.translateX = translatedX
            selectedImageView!!.translateY = translatedY
        }
    }

    override fun resetAnImage() {
        if (selectedImageView != null) {
            var translatedX:Double = selectedImageView!!.translateX
            var translatedY:Double = selectedImageView!!.translateY
            selectedImageView!!.translateX = 0.0
            selectedImageView!!.translateY = 0.0
            selectedImageView!!.rotate = 0.0
            selectedImageView!!.scaleX = 1.0
            selectedImageView!!.scaleY = 1.0
            selectedImageView!!.translateX = translatedX
            selectedImageView!!.translateY = translatedY
        }
    }

    override fun changeToTileViewMode() {
        if (tileView) {
            var numPicInCol = 0
            var heightCurrentCol = 0.0
            for (i in 0 until imagesCount) {
                imageViews[i].translateX = 0.0
                imageViews[i].translateY = 0.0
                imageViews[i].rotate = 0.0
                imageViews[i].scaleX = 1.0
                imageViews[i].scaleY = 1.0
                var ratio:Double = imageViews[i].boundsInParent.width / imageViews[i].image.width
                var imageHeight:Double = imageViews[i].image.height * ratio
                var col:Int = (scrollPaneWidth / 320).toInt()
                imageViews[i].translateX = (i % col) * 320.0
                if (i - col < 0) {
                    imageViews[i].translateY = 0.0
                } else {
                    var ratio = imageViews[i-col].boundsInParent.width / imageViews[i-col].image.width
                    imageViews[i].translateY = imageViews[i-col].translateY + (imageViews[i-col].image.height * ratio)
                }
//                if (heightCurrentCol + imageHeight < 650) {
//                    imageViews[i].translateX = numPicInCol * 320.0
//                    imageViews[i].translateY = heightCurrentCol
//                    heightCurrentCol += imageHeight
//                } else if (imageHeight >= 650) {
//                    numPicInCol ++
//                    heightCurrentCol = 0.0
//                    imageViews[i].translateX = numPicInCol * 320.0
//                    heightCurrentCol += imageHeight
//                } else {
//                    numPicInCol ++
//                    heightCurrentCol = 0.0
//                    imageViews[i].translateX = numPicInCol * 320.0
//                    heightCurrentCol += imageHeight
//                }
            }
        }
    }

    override fun returnToCascade() {
        if (!tileView) {
            for (i in 0 until imagesCount) {
                imageViews[i].translateX = (0..200).random() * 1.0
                imageViews[i].translateY = (0..200).random() * 1.0
                imageViews[i].fitWidth = 320.0
                imageViews[i].isPreserveRatio = true
            }
        }
    }

    override fun deselectImage() {
        if(selectedImageView != null) {
            selectedImageView?.effect = null
        }
        selectedImageView = null
        selectedImageName = null
    }

    override fun addModel(myModel: Model) {
        model = myModel
    }
}