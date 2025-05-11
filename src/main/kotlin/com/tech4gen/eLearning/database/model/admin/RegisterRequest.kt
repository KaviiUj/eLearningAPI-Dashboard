package com.tech4gen.eLearning.database.model.admin

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @field:NotBlank(message = "Username is required")
    val userName: String,

    @field:NotBlank(message = "Password cannot be empty")
    @field:Size(min = 8, message = "Password must be at least 6 characters")
    val password: String,

    @field:NotNull(message = "Role is required")
    @field:Min(value = 3, message = "Role must be at least 3")
    val role: Int
)
