package com.tech4gen.eLearning.database.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("papers")
data class Paper(
    @Id val id: ObjectId = ObjectId(),
    val paperTitle: String,
    val subjectId: String,
    val schoolId: String? = null,
    val year: Int,
    val paperType: Int,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now(),
)

/**
 * 1st term: 1
 * 2nd term: 2
 * 3rd term: 3
 * past paper: 4
 */
