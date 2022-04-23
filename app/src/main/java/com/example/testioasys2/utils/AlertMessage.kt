package com.example.testioasys2.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.testioasys2.R


fun showAlertDialog(context: Context, message: Int) {
    val alert = AlertDialog.Builder(context)
    alert.setTitle(R.string.error)
    alert.setMessage(message)
    alert.setCancelable(false)
    alert.setIcon(android.R.drawable.ic_dialog_alert)
    alert.setPositiveButton(android.R.string.ok) { _, _ -> }
    alert.create()
    alert.show()
}
