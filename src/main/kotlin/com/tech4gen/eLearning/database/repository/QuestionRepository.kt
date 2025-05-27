package com.tech4gen.eLearning.database.repository

import com.tech4gen.eLearning.database.document.Question
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface QuestionRepository: MongoRepository<Question, ObjectId> {
}