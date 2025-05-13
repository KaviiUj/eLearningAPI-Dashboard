package com.tech4gen.eLearning.service

import com.tech4gen.eLearning.database.document.Stream
import com.tech4gen.eLearning.database.model.stream.StreamListResponse
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
        imageUrl: String,
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
            userId = userId,
            imageUrl = imageUrl
        )
        streamRepository.save(stream)

        return StreamResponse(
            message = "Stream Created Successfully",
            streamName = stream.streamName,
            streamId = stream.id.toHexString(),
            imageUrl = stream.imageUrl,
            createdAt = stream.createdAt.toString()
        )
    }

    fun getAllStreams(): StreamListResponse {

        val dataList =  streamRepository.findAll().map { stream ->
            StreamResponse(
                message = "Stream Retrieved Successfully",
                streamName = stream.streamName,
                streamId = stream.id.toHexString(),
                imageUrl = stream.imageUrl,
                createdAt = stream.createdAt.toString()
            )
        }
        return if (dataList.isNotEmpty()) {
            StreamListResponse(
                message = "Streams Retrieved Successfully",
                streams = dataList
            )
        } else {
            throw UsernameNotFoundException("Streams not found")
        }
    }

    fun updateStream(
        streamId: String,
        streamName: String,
        userId: String
    ):StreamResponse {
        if (streamId.length != 24) {
            throw BadCredentialsException("Invalid stream id!")
        }
        if (userId.length != 24) {
            throw BadCredentialsException("Invalid user id!")
        }

        val stream = streamRepository.findById(ObjectId(streamId))
            .orElseThrow { UsernameNotFoundException("Stream not found!") }

        val existingAdmin = adminRepository.findById(ObjectId(userId))
            .orElseThrow { UsernameNotFoundException("Admin not found!") }

        val existingStream = streamRepository.findByStreamName(streamName)
        if (existingStream != null) {
            throw HttpClientErrorException(HttpStatus.CONFLICT,"Stream name already exists")
        }

        val updatedSteam = stream.copy(
            streamName = streamName,
            userId = userId
        )

        streamRepository.save(updatedSteam)

        return StreamResponse(
            message = "Stream updated successfully",
            streamName = streamName,
            streamId = stream.id.toHexString(),
            createdAt = stream.createdAt.toString()
        )
    }

    fun deleteStream(
        streamId: String
    ): StreamResponse {

        if (streamId.length != 24) {
            throw BadCredentialsException("Invalid stream id!")
        }

        val stream = streamRepository.findById(ObjectId(streamId))
            .orElseThrow { UsernameNotFoundException("Stream not found!") }

        streamRepository.deleteById(stream.id)

        return StreamResponse(
            message = "Stream deleted successfully",
            streamName = stream.streamName,
            streamId = stream.id.toHexString(),
            createdAt = stream.createdAt.toString()
        )
    }
}