package com.tech4gen.eLearning.database.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("syllabus")
data class Syllabus(
    @Id val id: ObjectId = ObjectId(),
    val syllabusName: String,
    val subjectId: String,
    val imageUrl: String? = null,
    val isActive: Boolean = true,
    val userId: String,
    val createdAt: Instant = Instant.now(),
    val updateAt: Instant = Instant.now()
)
