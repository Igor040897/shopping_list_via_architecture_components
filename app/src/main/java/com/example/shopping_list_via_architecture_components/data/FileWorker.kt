package com.example.shopping_list_via_architecture_components.data

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.shopping_list_via_architecture_components.BuildConfig
import com.example.shopping_list_via_architecture_components.createImageFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


private const val FILE_PROVIDER_AUTHORITY = "${BuildConfig.APPLICATION_ID}.fileprovider"
private const val FOLDER_IMAGE = "image"
const val IMAGE_EXTENSION = ".jpg"

class FileWorker(val context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: FileWorker? = null

        fun getInstance(context: Context): FileWorker {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FileWorker(context).also { INSTANCE = it }
            }
        }
    }

    fun getTempImageFileUri(name: String): Uri? {
        val photoFile: File? = try {
            context.createImageFile(name)
        } catch (ex: IOException) {
            null
        }
        return photoFile?.let { file ->
            FileProvider.getUriForFile(
                context,
                FILE_PROVIDER_AUTHORITY,
                file
            )
        }
    }

    fun saveImage(imageUri: Uri, name: String): String? {
        val openInputStream = context.contentResolver.openInputStream(imageUri)
        var file: File? = null
        openInputStream?.use { openInputStream ->
            file = File(getImageDir(), name)
            FileOutputStream(file).use { output ->
                val buffer = ByteArray(4 * 1024)
                var read: Int
                while (openInputStream.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
        }
        return file?.absolutePath
    }

    private fun getImageDir(): File? {
        val storageDir = File(context.filesDir, FOLDER_IMAGE)
        val success = if (!storageDir.exists()) {
            storageDir.mkdirs()
        } else true
        return storageDir.takeIf { success }
    }
}
