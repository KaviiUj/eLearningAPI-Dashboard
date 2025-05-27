package com.tech4gen.eLearning.database.model.question

data class QuestionResponse(
    val questionId: String,
    val message: String,
    val createdAt: String = "",
    val updatedAt: String = "",
)

data class QuestionsResponse(
    val questionId: String,
    val paperId: String,
    val questionText: String,
    val questionUrl: String,
    val answers: List<String>,
    val correctAnswer: String,
    val subjectId: String,
    val subjectName: String,
    val schoolId: String,
    val schoolName: String,
    val year: Int,
    val paperType: Int,
    val syllabusId: String = "",
    val syllabusName: String = "",
)

data class QuestionListResponse(
    val questions: List<QuestionsResponse>,
    val message: String
)

