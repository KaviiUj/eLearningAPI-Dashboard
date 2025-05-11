package com.tech4gen.eLearning.service

import com.tech4gen.eLearning.database.document.Stream
import com.tech4gen.eLearning.database.model.stream.StreamResponse
import com.tech4gen.eLearning.database.repository.AdminRepository
import com.tech4gen.eLearning.database.repository.StreamRepository
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class StreamService(
    private val streamRepository: StreamRepository,
    private val adminRepository: AdminRepository
) {
    fun createStream(
        streamName: String,
        userId: String,
    ): StreamResponse {

        val existingStream = streamRepository.findByStreamName(streamName)
        if (existingStream != null) {
            throw HttpClientErrorException(HttpStatus.CONFLICT,"Stream name already exists")
        }

        if (userId.length != 24) {
            throw BadCredentialsException("Invalid user id!")
        }

        val existingAdmin = adminRepository.findById(ObjectId(userId))
            .orElseThrow { UsernameNotFoundException("Admin not found!") }

        val stream = Stream(
            streamName = streamName,
            userId = userId
        )
        streamRepository.save(stream)

        return StreamResponse(
            message = "Stream Created Successfully",
            streamName = stream.streamName,
            streamId = stream.id.toHexString(),
            createdAt = stream.createdAt.toString()
        )
    }
}