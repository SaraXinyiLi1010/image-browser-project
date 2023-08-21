import javafx.beans.property.ReadOnlyDoubleProperty
import java.io.File

class Model {
    private val views = ArrayList<IView>()
    public val files = ArrayList<File>()
    private var selectedImageName:String? = null

    // view management
    fun addView(view: IView) {
        views.add(view)
    }
    fun addImage(file:File) {
        for(view in views) {
            view.addImage(file)
        }
    }

    fun updateSelectedImage(imageName: String) {
        selectedImageName = imageName
        for (view in views) {
            view.updateSelectedImageName(imageName)
        }
    }

    fun deselectImage() {
        for (view in views) {
            view.deselectImage()
        }
    }

    fun deleteSelected () {
        if(selectedImageName != null) {
            selectedImageName = null
            for (view in views) {
                view.deleteAnImage()
            }
        }
    }

    fun zoomInSelected() {
        for (view in views) {
            view.zoomInAnImage()
        }
    }

    fun zoomOutSelected() {
        for (view in views) {
            view.zoomOutAnImage()
        }

    }

    fun rotateLeftSelected() {
        for (view in views) {
            view.rotateLeftAnImage()
        }
    }

    fun rotateRightSelected() {
        for (view in views) {
            view.rotateRightAnImage()
        }
    }

    fun resetImageSelected() {
        for (view in views) {
            view.resetAnImage()
        }
    }

    fun updateTileView (tileView:Boolean) {
        for (view in views) {
            view.tileView = tileView
        }
        if (tileView) {
            for (view in views) {
               view.changeToTileViewMode()
            }
        }
        if (!tileView) {
            for (view in views) {
                view.returnToCascade()
            }
        }
    }

    fun notifyViews() {
        for (view in views) {
            view.update()
        }
    }
}