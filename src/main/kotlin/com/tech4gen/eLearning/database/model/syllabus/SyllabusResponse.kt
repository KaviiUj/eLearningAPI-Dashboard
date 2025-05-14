package com.tech4gen.eLearning.database.model.syllabus

data class SyllabusResponse(
    val syllabusName: String,
    val syllabusId: String,
    val subjectId: String,
    val subjectName: String,
    val userId: String,
    val imageUrl: String? = null,
    val isActive: Boolean = true,
    val createdAt: String,
)

data class SyllabusListResponse(
    val message: String,
    val syllabusList: List<SyllabusResponse>
)