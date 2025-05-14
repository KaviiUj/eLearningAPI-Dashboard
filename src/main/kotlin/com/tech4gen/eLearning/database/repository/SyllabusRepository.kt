package com.tech4gen.eLearning.database.repository

import com.tech4gen.eLearning.database.document.Syllabus
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface SyllabusRepository: MongoRepository<Syllabus, ObjectId> {
    fun findBySyllabusName(syllabusName: String): Syllabus?
    fun findBySubjectId(subjectId: String): List<Syllabus>
}