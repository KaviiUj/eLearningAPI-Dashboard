package com.tech4gen.eLearning.database.model.paper

data class PaperResponse(
    val id: String,
    val paperTitle: String,
    val subjectName: String,
    val subjectId: String,
    val schoolName: String? = null,
    val schoolId: String? = null,
    val year: Int,
    val paperType: Int,
    val createdAt: String,
)

data class PaperListResponse(
    val message: String,
    val papers: List<PaperResponse>,
    val totalPages: Int = 0,
    val currentPage: Int = 1,
    val pageSize: Int = 0,
)