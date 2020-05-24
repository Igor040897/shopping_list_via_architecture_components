package com.example.shopping_list_via_architecture_components.ui.itemsList

import android.R
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private const val MESSAGE_KEY = "MESSAGE_KEY"
private const val ALERT_DIALOG_THEME = "AlertDialogTheme"
private const val STYLE = "style"

class AlertDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(message: Int) = AlertDialogFragment().apply {
            arguments = bundleOf(MESSAGE_KEY to message)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val message = arguments?.getInt(MESSAGE_KEY)
        return MaterialAlertDialogBuilder(activity, resources.getIdentifier(ALERT_DIALOG_THEME, STYLE, activity?.packageName))
            .apply {
                if (message != null) setMessage(message)
            }
            .setPositiveButton(R.string.ok) { _, _ ->
                targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, Intent())
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                targetFragment?.onActivityResult(
                    targetRequestCode,
                    Activity.RESULT_CANCELED,
                    Intent()
                )
            }
            .create()
    }

}