package com.tech4gen.eLearning.service

import com.tech4gen.eLearning.database.document.Syllabus
import com.tech4gen.eLearning.database.model.syllabus.SyllabusListResponse
import com.tech4gen.eLearning.database.model.syllabus.SyllabusRequest
import com.tech4gen.eLearning.database.model.syllabus.SyllabusResponse
import com.tech4gen.eLearning.database.repository.AdminRepository
import com.tech4gen.eLearning.database.repository.SubjectRepository
import com.tech4gen.eLearning.database.repository.SyllabusRepository
import com.tech4gen.eLearning.util.dataLogger
import org.bson.types.ObjectId
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class SyllabusService(
    private val syllabusRepository: SyllabusRepository,
    private val subjectRepository: SubjectRepository,
    private val adminRepository: AdminRepository,
) {

    fun createSyllabus(
        syllabusList: List<SyllabusRequest>
    ): SyllabusListResponse {
        val finalList: MutableList<SyllabusResponse> = arrayListOf()
        syllabusList.forEach { syllabus ->
            if (syllabus.userId.length != 24 || syllabus.userId.isEmpty()) throw BadCredentialsException("Invalid user id!")
            if (syllabus.subjectId.length != 24 || syllabus.subjectId.isEmpty()) throw BadCredentialsException("Invalid subject id!")
            if (syllabus.syllabusName.isEmpty()) throw BadCredentialsException("Syllabus name cannot be empty!")

            val findSubject = subjectRepository.findById(ObjectId(syllabus.subjectId))
            val findAdmin = adminRepository.findById(ObjectId(syllabus.userId))
            val findSyllabus = syllabusRepository.findBySyllabusName(syllabus.syllabusName)

            if (!findSubject.isPresent) throw UsernameNotFoundException("Subject not found")
            if (!findAdmin.isPresent) throw UsernameNotFoundException("User not found")
            dataLogger("findSyllabus: ${findSyllabus}")
            if (findSyllabus != null) throw BadCredentialsException("Syllabus already exists!")



            val syllabusData = Syllabus(
                syllabusName = syllabus.syllabusName,
                subjectId = syllabus.subjectId,
                userId = syllabus.userId,
                imageUrl = syllabus.imageUrl,
                isActive = true
            )

            syllabusRepository.save(syllabusData)
            val dataList = SyllabusResponse(
                imageUrl = syllabus.imageUrl ?: "",
                isActive = syllabusData.isActive,
                subjectId = syllabus.subjectId,
                subjectName = findSubject.get().subjectName,
                userId = syllabus.userId,
                syllabusId = syllabusData.id.toHexString(),
                syllabusName = syllabus.syllabusName,
                createdAt = syllabusData.createdAt.toString(),
            )
            finalList.add(dataList)
        }

        return SyllabusListResponse(
            message = "Syllabus list created successfully",
            syllabusList = finalList
        )
    }
}