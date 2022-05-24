package com.example.testioasys2.utils

import android.content.Context
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