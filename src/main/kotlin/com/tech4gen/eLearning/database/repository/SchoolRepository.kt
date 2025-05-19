package com.tech4gen.eLearning.database.repository

import com.tech4gen.eLearning.database.document.School
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface SchoolRepository: MongoRepository<School, ObjectId> {
}