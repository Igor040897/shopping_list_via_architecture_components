package com.example.shopping_list_via_architecture_components.ui.itemsList

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.DialogFragment
import com.example.shopping_list_via_architecture_components.R
import com.example.shopping_list_via_architecture_components.databinding.FragmentItemsListBinding
import com.example.shopping_list_via_architecture_components.observeCommand
import com.example.shopping_list_via_architecture_components.observeOnView
import com.example.shopping_list_via_architecture_components.ui.addItem.AddItemActivity
import com.example.shopping_list_via_architecture_components.ui.base.BaseFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

const val REQUEST_CODE_ADD_ITEM = 99
const val REQUEST_CODE_ALERT_DIALOG_FRAGMENT = 100
const val ALERT_DIALOG_FRAGMENT_TAG = "ALERT_DIALOG_FRAGMENT_TAG"

class ItemsListFragment : BaseFragment<FragmentItemsListBinding>() {

    companion object {
        fun newInstance() = ItemsListFragment()
    }

    override val contentLayoutId = R.layout.fragment_items_list
    override var title = R.string.label_shopping_list

    @Inject
    lateinit var viewModel: ItemListViewModel
    private var actionMode: ActionMode? = null
    private val actionModeCallback: ActionMode.Callback =
        object : ActionMode.Callback {
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                item?.run {
                    when (item.itemId) {
                        R.id.item_select_all -> {
                            val countSelected = viewModel.selectAll()
                            actionMode?.title = "$countSelected выбрано"
                        }
                        R.id.item_shop -> {
                            showDialog()
                        }
                        else -> Unit
                    }
                }
                return false
            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.menuInflater?.inflate(R.menu.menu_action_mode, menu)
                binding.fab.hide()
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                actionMode = null
                mode?.finish()
                viewModel.clearItemSelections()
                binding.fab.show()
            }
        }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun setupBinding(binding: FragmentItemsListBinding) {
        super.setupBinding(binding)
        binding.viewModel = viewModel
        binding.fab.setOnClickListener {
            val intent = Intent(context, AddItemActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_ITEM)
        }
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.apply {
            observeCommand(longClickItemCommand) {
                if (actionMode == null) {
                    actionMode = (activity as? AppCompatActivity)
                        ?.startSupportActionMode(actionModeCallback)
                }
            }
            observeCommand(selectedItemCommand) {
                actionMode?.title = if (it == 0) "" else "$it выбрано"
            }
            observeOnView(allNotPurchaseProducts) {
                setItems(it)
            }
        }
    }

    fun showDialog() {
        fragmentManager?.apply {
            val newFragment: DialogFragment =
                AlertDialogFragment.newInstance(R.string.shop_products_message)
            newFragment.setTargetFragment(
                this@ItemsListFragment,
                REQUEST_CODE_ALERT_DIALOG_FRAGMENT
            )
            newFragment.show(this, ALERT_DIALOG_FRAGMENT_TAG)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ALERT_DIALOG_FRAGMENT) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.shopSelectItems()
                actionMode?.finish()
            }
        }
    }
}