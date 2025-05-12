package com.tech4gen.eLearning.database.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("subjects")
data class Subject(
    @Id val id: ObjectId = ObjectId(),
    val subjectName: String,
    val imageUrl: String? = null,
    val streamId: String,
    val isActive: Boolean = true,
    val createdAt: Instant = Instant.now(),
    val updateAt: Instant = Instant.now(),
)
