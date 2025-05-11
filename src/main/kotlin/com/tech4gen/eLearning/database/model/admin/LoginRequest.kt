package com.tech4gen.eLearning.database.model.admin

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest(
    @field:NotBlank(message = "Username is required")
    val userName: String,

    @field:NotBlank(message = "Password cannot be empty")
    @field:Size(min = 8, message = "Password must be at least 6 characters")
    val password: String,
)
