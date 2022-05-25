package com.example.testioasys2.utils

import androidx.appcompat.widget.SearchView

fun SearchView.onQueryTextChange(text: (String?) -> Unit){
    setOnQueryTextListener(object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            text.invoke(newText)
            return false
        }
    })
}