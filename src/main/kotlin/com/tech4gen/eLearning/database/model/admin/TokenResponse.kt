package com.tech4gen.eLearning.database.model.admin

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val message: String,
    val userId: String,
)