package com.example.testioasys2.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import com.example.testioasys2.R


fun Context.showAlertDialog(message: Int) {
    AlertDialog.Builder(this).apply {
        setTitle(R.string.error)
        setMessage(message)
        setCancelable(false)
        setIcon(android.R.drawable.ic_dialog_alert)
        setPositiveButton(android.R.string.ok) { _, _ -> }
        create()
        show()
    }
}

fun Context.createLoadingDialog(): Dialog {
    val builder = AlertDialog.Builder(this)
    builder.setView(R.layout.dialog_load)
    builder.setCancelable(false)

    val dialog = builder.create()
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    return dialog
}