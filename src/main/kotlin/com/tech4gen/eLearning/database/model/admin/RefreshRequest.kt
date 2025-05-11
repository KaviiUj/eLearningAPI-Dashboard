package com.tech4gen.eLearning.database.model.admin

import jakarta.validation.constraints.NotBlank

data class RefreshRequest(
    @field:NotBlank(message = "Refresh token is required")
    val refreshToken: String
)
