package com.tech4gen.eLearning.database.model.syllabus

import jakarta.validation.constraints.NotBlank

data class SyllabusRequest(
    val imageUrl: String? = null,
    val isActive: Boolean = true,

    @field:NotBlank(message = "Subject id cannot be blank")
    val subjectId: String,

    @field:NotBlank(message = "User id cannot be blank")
    val userId: String,

    @field:NotBlank(message = "Syllabus name cannot be blank")
    val syllabusName: String
)