package com.tech4gen.eLearning.database.model.stream

import jakarta.validation.constraints.NotBlank

data class StreamRequest(
    @field:NotBlank(message = "Stream name cannot be empty")
    val streamName: String,

    @field:NotBlank(message = "Userid cannot be empty")
    val userId: String
)
