package com.tech4gen.eLearning.database.model.subject

data class SubjectResponse(
    val message: String,
    val subjectName: String,
    val subjectId: String,
    val streamId: String,
    val createdAt: String,
    val imageUrl: String? = null
)

data class SubjectListResponse(
    val message: String,
    val subjects: List<SubjectResponse>
)
