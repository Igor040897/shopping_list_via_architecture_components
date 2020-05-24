package com.example.shopping_list_via_architecture_components.ui.addItem

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.shopping_list_via_architecture_components.R
import com.example.shopping_list_via_architecture_components.databinding.ActivityAddItemBinding
import com.example.shopping_list_via_architecture_components.setImage
import com.example.shopping_list_via_architecture_components.ui.addItem.addPhoto.AddPhotoDialogFragment
import com.example.shopping_list_via_architecture_components.ui.addItem.addPhoto.OnDialogAddPhotoResultListener
import com.example.shopping_list_via_architecture_components.ui.base.BaseActivity
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import javax.inject.Inject

const val PERMISSION_ACCESS_WRITE_READ_EXTERNAL_STORAGE = 15

class AddItemActivity : BaseActivity<ActivityAddItemBinding>(), OnDialogAddPhotoResultListener,
    AddItemActionListener {
    override val contentLayoutId: Int
        get() = R.layout.activity_add_item

//    @Inject
//    lateinit var viewModelFactory: ViewModelFactoryTest

    @Inject
    lateinit var viewModel: AddItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun setupBinding(binding: ActivityAddItemBinding) {
        setupActionBar()
        binding.actionListener = this
        val viewModel1 = viewModel
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

    }

    override fun onAddItemClick() {
        if (binding.nameTextView.text?.isNotEmpty() == true || viewModel.hasImage()) {
            binding.nameTextView.text.toString().run {
                viewModel.saveItemProduct(this)
                finish()
            }
        } else {
            Snackbar.make(binding.root, R.string.edd_item_hint, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onAddPhotoClick() {
        if (checkPermissions()) {
            val addPhotoDialog = AddPhotoDialogFragment.getInstance()
            addPhotoDialog.show(supportFragmentManager, addPhotoDialog::class.java.simpleName)
        }
    }

    //For access to write and read (MediaStore.ACTION_IMAGE_CAPTURE, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    //don't need call permission. This made just as example
    private fun checkPermissions(): Boolean {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) &&
            (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)
        ) {
            return true
        }

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            PERMISSION_ACCESS_WRITE_READ_EXTERNAL_STORAGE
        )
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_ACCESS_WRITE_READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    val addPhotoDialog = AddPhotoDialogFragment.getInstance()
                    addPhotoDialog.show(
                        supportFragmentManager,
                        addPhotoDialog::class.java.simpleName
                    )
                } else {
                    Snackbar.make(binding.root, R.string.file_access_canceled, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun resultPhoto(uri: Uri) {
        binding.photoImageView.setImage(uri)
        viewModel.saveImage(uri)
    }
}