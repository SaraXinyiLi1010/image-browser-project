import java.io.File

interface IView {
    var imagesCount:Int
    var selectedImageName:String?
    var tileView:Boolean
    fun update()
    fun addModel(myModel: Model)
    fun addImage(file: File)
    fun deleteAnImage()
    fun zoomInAnImage()
    fun zoomOutAnImage()
    fun rotateLeftAnImage()
    fun rotateRightAnImage()
    fun resetAnImage()
    fun changeToTileViewMode()
    fun returnToCascade()
    fun deselectImage()
    fun updateSelectedImageName(name:String)
}