package com.tech4gen.eLearning.database.repository

import com.tech4gen.eLearning.database.document.RefreshToken
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface RefreshTokenRepository: MongoRepository<RefreshToken, ObjectId> {

    fun findByUserIdAndHashedToken(
        userId: ObjectId,
        hashedToken: String
    ): RefreshToken?

    fun deleteByUserIdAndHashedToken(
        userId: ObjectId,
        hashedToken: String
    )

    fun deleteByUserId(userId: ObjectId)
}