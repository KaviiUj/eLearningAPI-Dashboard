package com.tech4gen.eLearning.database.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("questions")
data class Question(
    @Id val id: ObjectId = ObjectId(),
    val paperId: String,
    val questionText: String,
    val questionUrl: String,
    val answers: List<String>,
    val correctAnswer: String,
    val subjectId: String,
    val subjectName: String,
    val schoolId: String,
    val schoolName: String,
    val year: Int,
    val paperType: Int,
    val syllabusId: String = "",
    val syllabusName: String = "",
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)
