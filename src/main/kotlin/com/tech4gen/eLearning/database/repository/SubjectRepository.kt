package com.tech4gen.eLearning.database.repository

import com.tech4gen.eLearning.database.document.Subject
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface SubjectRepository: MongoRepository<Subject, ObjectId> {
    fun findBySubjectName(subjectName: String): Subject?
}