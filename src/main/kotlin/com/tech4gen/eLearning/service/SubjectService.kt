package com.tech4gen.eLearning.service

import com.tech4gen.eLearning.database.document.Subject
import com.tech4gen.eLearning.database.model.subject.SubjectListResponse
import com.tech4gen.eLearning.database.model.subject.SubjectResponse
import com.tech4gen.eLearning.database.repository.StreamRepository
import com.tech4gen.eLearning.database.repository.SubjectRepository
import com.tech4gen.eLearning.util.Loggable
import org.bson.types.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import java.io.Console

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository,
    private val streamRepository: StreamRepository
): Loggable {

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

    fun getAllSubjects(
        streamId: String
    ): SubjectListResponse {
        if (streamId.length != 24) {
            throw BadCredentialsException("Invalid stream id!")
        }
        val findStream = streamRepository.findById(ObjectId(streamId))
        if (!findStream.isPresent) {
            throw UsernameNotFoundException("Stream not found")
        }

        val subjectList = subjectRepository.findByStreamId(streamId)
        val dataList =  subjectList.map { subject ->
            SubjectResponse(
                message = "Subject Retrieved Successfully",
                subjectName = subject.subjectName,
                subjectId = subject.id.toHexString(),
                streamId = subject.streamId,
                createdAt = subject.createdAt.toString()
            )
        }

        return if (dataList.isEmpty()) {
            throw UsernameNotFoundException("Subject not found")
        } else {
            SubjectListResponse(
                message = "Subjects list found",
                subjects = dataList
            )
        }
    }
}