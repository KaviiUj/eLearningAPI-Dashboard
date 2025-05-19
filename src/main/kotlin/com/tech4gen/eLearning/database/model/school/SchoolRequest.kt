package com.tech4gen.eLearning.database.model.school

import jakarta.validation.constraints.NotBlank

data class SchoolRequest(
    @field:NotBlank(message = "address cannot be empty")
    val address: String,

    val censusNumber: String? = null,

    @field:NotBlank(message = "district cannot be empty")
    val district: String,

    val medium: String? = null,
    val nsPs: String? = null,

    @field:NotBlank(message = "province cannot be empty")
    val province: String,

    @field:NotBlank(message = "school name cannot be empty")
    val school: String,

    val stage: String? = null,

    @field:NotBlank(message = "telephoneNo cannot be empty")
    val telephoneNo: String,

    @field:NotBlank(message = "province cannot be empty")
    val zone: String
)