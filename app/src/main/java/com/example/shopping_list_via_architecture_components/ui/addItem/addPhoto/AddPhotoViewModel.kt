package com.example.shopping_list_via_architecture_components.ui.addItem.addPhoto

import android.app.Application
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.shopping_list_via_architecture_components.data.Repository
import com.example.shopping_list_via_architecture_components.ui.base.BaseViewModel
import javax.inject.Inject

private const val RESULT_FILE_NAME = "user_pic"

class AddPhotoViewModel (
    application: Application,
    private val repository: Repository
) : BaseViewModel(application) {

    val returnResultCommand = MutableLiveData<Uri?>()
    val makePhotoCommand = MutableLiveData<Uri>()

    var imageUri: Uri? = null
        set(value) {
            field = value
            returnResultCommand.postValue(field)
        }

    private var photoUri: Uri? = null

    fun makePhoto() {
        if (photoUri == null) {
            photoUri = repository.getTempImageFileUri(RESULT_FILE_NAME)
        }
        photoUri?.let {
            makePhotoCommand.postValue(it)
        }
    }


    fun photoTaken() {
        returnResultCommand.postValue(photoUri)
    }
}