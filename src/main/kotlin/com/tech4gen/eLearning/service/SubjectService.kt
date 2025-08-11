package com.tech4gen.eLearning.service

import com.tech4gen.eLearning.database.document.Subject
import com.tech4gen.eLearning.database.model.subject.SubjectListResponse
import com.tech4gen.eLearning.database.model.subject.SubjectResponse
import com.tech4gen.eLearning.database.repository.StreamRepository
import com.tech4gen.eLearning.database.repository.SubjectRepository
import com.tech4gen.eLearning.util.dataLogger
import com.tech4gen.eLearning.util.reqLogger
import com.tech4gen.eLearning.util.resLogger
import com.tech4gen.eLearning.util.toJson
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
        streamId: String,
        imageUrl: String,
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
            throw HttpClientErrorException(HttpStatus.CONFLICT, "Subject name already exists")
        }

        val subject = Subject(
            subjectName = subjectName,
            streamId = streamId,
            imageUrl = imageUrl,
        )
        subjectRepository.save(subject)

        return SubjectResponse(
            message = "Subject Created Successfully",
            subjectName = subject.subjectName,
            subjectId = subject.id.toHexString(),
            streamId = subject.streamId,
            createdAt = subject.createdAt.toString(),
            imageUrl = subject.imageUrl
        )
    }

    fun getAllSubjects(
        streamId: String,
        subject1: String,
        subject2: String,
        subject3: String,
    ): SubjectListResponse {

        if (streamId.length != 24) {
            throw BadCredentialsException("Invalid stream id!")
        }

        val findStream = streamRepository.findById(ObjectId(streamId))
        dataLogger("findStream: ${findStream.get().streamName}")

        if (!findStream.isPresent) {
            throw UsernameNotFoundException("Stream not found")
        }

        val existingSubject1 = subjectRepository.findBySubjectName(subjectName = subject1)
        val existingSubject2 = subjectRepository.findBySubjectName(subjectName = subject2)
        val existingSubject3 = subjectRepository.findBySubjectName(subjectName = subject3)

        reqLogger("existingSubject1: ${existingSubject1?.subjectName}")
        reqLogger("existingSubject2: ${existingSubject2?.subjectName}")

        val subList: MutableList<SubjectResponse> = arrayListOf()

        listOfNotNull(subject1, subject2, subject3).forEach { subjectName ->
            subjectRepository.findBySubjectName(subjectName)?.let { subject ->
                subList.add(subject.toSubjectResponse())
            }
        }

        resLogger("subList: ${subList.toJson()}")

        val subjectList = subjectRepository.findByStreamId(streamId)
        subjectList.map { subject ->
            val sub = SubjectResponse(
                message = "${subject.subjectName} found",
                subjectName = subject.subjectName,
                subjectId = subject.id.toHexString(),
                streamId = subject.streamId,
                createdAt = subject.createdAt.toString(),
                imageUrl = subject.imageUrl
            )
            subList.add(sub)
        }

        return if (subList.isEmpty()) {
            throw UsernameNotFoundException("Subject not found")
        } else {
            SubjectListResponse(
                message = "Subjects list found",
                subjects = subList
            )
        }
    }

    fun getAllAvailableSubjects(): SubjectListResponse {

        val allSubjects = subjectRepository.findAll()
        if (allSubjects.isEmpty()) {
            throw UsernameNotFoundException("No subjects found in the database")
        }

        val subjectResponseList = allSubjects.map { it.toSubjectResponse() }
        return SubjectListResponse(
            message = "All available subjects retrieved successfully",
            subjects = subjectResponseList
        )
    }

    private fun Subject.toSubjectResponse() = SubjectResponse(
        message = "${this.subjectName} found",
        subjectName = this.subjectName,
        subjectId = this.id.toHexString(),
        streamId = this.streamId,
        createdAt = this.createdAt.toString(),
        imageUrl = this.imageUrl
    )
}