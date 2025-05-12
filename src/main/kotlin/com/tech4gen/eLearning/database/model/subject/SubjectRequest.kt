package com.tech4gen.eLearning.database.model.subject

import jakarta.validation.constraints.NotBlank

data class SubjectRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,
    val imageUrl: String? = null,
    @field:NotBlank(message = "Stream ID is required")
    val streamId: String,
    val isActive: Boolean = true,
)