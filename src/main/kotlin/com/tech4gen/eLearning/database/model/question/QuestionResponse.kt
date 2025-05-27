package com.tech4gen.eLearning.database.model.question

data class QuestionResponse(
    val questionId: String,
    val message: String,
    val createdAt: String
)

data class QuestionListResponse(
    val questions: List<QuestionResponse>,
    val message: String
)

