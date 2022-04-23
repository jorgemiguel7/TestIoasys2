package com.example.testioasys2.domain.model

import java.io.Serializable

data class UserSession(
    val accessToken: String,
    val client: String,
    val uid: String
): Serializable