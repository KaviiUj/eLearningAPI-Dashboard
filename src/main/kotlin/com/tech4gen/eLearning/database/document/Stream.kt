package com.tech4gen.eLearning.database.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("streams")
data class Stream(
    val streamName: String,
    val userId: String,
    @Id val id: ObjectId = ObjectId(),
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)
