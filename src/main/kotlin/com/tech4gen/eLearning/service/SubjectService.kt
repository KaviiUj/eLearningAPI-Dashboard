package com.tech4gen.eLearning.service

import com.tech4gen.eLearning.database.document.Subject
import com.tech4gen.eLearning.database.model.subject.SubjectResponse
import com.tech4gen.eLearning.database.repository.StreamRepository
import com.tech4gen.eLearning.database.repository.SubjectRepository
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository,
    private val streamRepository: StreamRepository
) {
    fun createSubject(
        subjectName: String,
        streamId: String
    ): SubjectResponse {
        if (streamId.length != 24) {
            throw BadCredentialsException("Invalid stream id!")
        }
        val findStream = streamRepository.findById(ObjectId(streamId))
        if (!findStream.isPresent) {
            throw UsernameNotFoundException("Stream not found")
        }

        val existingSubject = subjectRepository.findBySubjectName(subjectName = subjectName)
        if (existingSubject != null) {
            throw HttpClientErrorException(HttpStatus.CONFLICT,"Subject name already exists")
        }

        val subject = Subject(
            subjectName = subjectName,
            streamId = streamId,
        )
        subjectRepository.save(subject)

        return SubjectResponse(
            message = "Subject Created Successfully",
            subjectName = subject.subjectName,
            subjectId = subject.id.toHexString(),
            streamId = subject.streamId,
            createdAt = subject.createdAt.toString()
        )
    }
}