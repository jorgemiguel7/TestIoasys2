package com.example.testioasys2.presentation.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

open class BaseActivity: AppCompatActivity() {

    protected fun setupToolbar(toolbar: Toolbar, titleIdRes: Int, showBackButton: Boolean = false){
        with(toolbar){
            removeAllViews()
            setTitle(titleIdRes)
        }
        setSupportActionBar(toolbar)
        if (showBackButton) supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}