package com.example.testioasys2.utils

object UserSession {
    private var accessToken: String? = null
    private var client: String? = null
    private var uid: String? = null

    fun setUserAccessCredentials(accessToken: String?, client: String?, uid: String?){
        this.accessToken = accessToken
        this.client = client
        this.uid = uid
    }
}