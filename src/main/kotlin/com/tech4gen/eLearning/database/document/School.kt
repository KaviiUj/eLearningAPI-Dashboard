package com.tech4gen.eLearning.database.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("school")
data class School(
    @Id val id: ObjectId = ObjectId(),
    val address: String,
    val censusNumber: String,
    val district: String,
    val medium: String,
    val nsPs: String,
    val province: String,
    val school: String,
    val stage: String,
    val telephoneNo: String,
    val zone: String,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now(),
)