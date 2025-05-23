package com.tech4gen.eLearning.database.model.paper

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class PaperRequest(
    @field:NotBlank(message = "Paper title is required")
    val paperTitle: String,
    @field:NotBlank(message = "Subject ID is required")
    val subjectId: String,
    val schoolId: String,
    @field:NotNull(message = "Year is required")
    val year: Int,
    @field:NotNull(message = "Paper type is required")
    val paperType: Int
)
