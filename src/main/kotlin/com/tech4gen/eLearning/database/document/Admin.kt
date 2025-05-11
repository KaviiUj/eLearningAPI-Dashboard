package com.tech4gen.eLearning.database.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("admins")
data class Admin(
    val userName: String,
    val password: String,
    val role: Int,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now(),
    @Id val id: ObjectId = ObjectId()
)