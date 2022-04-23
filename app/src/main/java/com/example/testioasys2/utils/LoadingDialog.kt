package com.example.testioasys2.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import com.example.testioasys2.R

object LoadingDialog {

    private lateinit var dialog: AlertDialog

    @SuppressLint("InflateParams")
    fun startLoading(context: Activity){
        val alertDialog = AlertDialog.Builder(context)
        val inflater = context.layoutInflater
        alertDialog.setView(inflater.inflate(R.layout.dialog_load, null))
        alertDialog.setCancelable(true)

        dialog = alertDialog.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.show()
    }

    fun finishLoading(){
        dialog.dismiss()
    }
}