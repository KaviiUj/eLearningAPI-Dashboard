package com.tech4gen.eLearning.database.repository

import com.tech4gen.eLearning.database.document.Admin
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface AdminRepository: MongoRepository<Admin, ObjectId> {
    fun findByUserName(userName: String): Admin?
}