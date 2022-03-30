package com.example.testioasys2.data

import com.example.testioasys2.utils.Constants

object UserSession {
    private lateinit var accessToken: String
    private lateinit var client: String
    private lateinit var uid: String

    fun setUserAccessCredentials(accessToken: String?, client: String?, uid: String?){
        this.accessToken = accessToken!!
        this.client = client!!
        this.uid = uid!!
    }

    fun getUserLoginCredentials(credential: String): String {
        return when(credential){
            Constants.ACCESS_TOKEN -> accessToken
            Constants.CLIENT -> client
            Constants.UID -> uid
            else -> ""
        }
    }
}