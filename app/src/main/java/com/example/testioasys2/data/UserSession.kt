package com.example.testioasys2.data

import java.io.Serializable

data class UserSession(
    val accessToken: String,
    val client: String,
    val uid: String
): Serializable