package com.tech4gen.eLearning.database.model.question

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.time.Instant

data class QuestionRequest(
    @field:NotBlank(message = "Question text must not be blank")
    val questionText: String,

    @field:NotBlank(message = "Paper Id must not be blank")
    val paperId: String,

    @field:NotEmpty(message = "Answers must not be empty")
    val answers: List<String>,

    @field:NotBlank(message = "Correct answer must not be blank")
    val correctAnswer: String,

    @field:NotBlank(message = "Subject ID must not be blank")
    val subjectId: String,

    @field:NotBlank(message = "Subject name must not be blank")
    val subjectName: String,

    @field:NotNull(message = "Year is required")
    val year: Int,

    @field:NotNull(message = "Paper type is required")
    val paperType: Int,

    val syllabusId: String,
    val syllabusName: String,
    val questionUrl: String,
    val schoolId: String,
    val schoolName: String,
)
