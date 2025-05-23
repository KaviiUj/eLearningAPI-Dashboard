package com.tech4gen.eLearning.database.repository

import com.tech4gen.eLearning.database.document.Paper
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PaperRepository: MongoRepository<Paper, ObjectId> {
}