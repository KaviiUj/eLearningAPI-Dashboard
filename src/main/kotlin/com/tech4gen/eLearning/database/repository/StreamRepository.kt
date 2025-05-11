package com.tech4gen.eLearning.database.repository

import com.tech4gen.eLearning.database.document.Stream
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface StreamRepository: MongoRepository<Stream, ObjectId> {
    fun findByStreamName(streamName: String): Stream?
    fun existsByStreamName(streamName: String): Boolean
    fun deleteByStreamName(streamName: String): Long
}