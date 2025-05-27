package com.tech4gen.eLearning.database.model.question

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class QuestionUpdateRequest(
    @field:NotBlank(message = "Question Id must not be blank")
    val questionId: String,

    @field:NotBlank(message = "Question text must not be blank")
    val questionText: String,

    @field:NotEmpty(message = "Answers must not be empty")
    val answers: List<String>,

    @field:NotBlank(message = "Correct answer must not be blank")
    val correctAnswer: String,

    @field:NotNull(message = "Paper type is required")
    val paperType: Int,

    val syllabusId: String,
    val syllabusName: String,
    val questionUrl: String,
)
