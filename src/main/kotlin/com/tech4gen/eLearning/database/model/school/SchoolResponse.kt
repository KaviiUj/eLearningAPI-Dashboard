package com.tech4gen.eLearning.database.model.school

data class SchoolResponse(
    val schoolId: String,
    val address: String,
    val censusNumber: String? = null,
    val district: String,
    val medium: String? = null,
    val nsPs: String? = null,
    val province: String,
    val school: String,
    val stage: String? = null,
    val telephoneNo: String,
    val zone: String,
    val createdAt: String
)

data class SchoolListResponse(
    val schoolList: List<SchoolResponse>,
    val message: String
)