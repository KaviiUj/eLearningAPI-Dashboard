package com.tech4gen.eLearning.service

import com.tech4gen.eLearning.database.document.Paper
import com.tech4gen.eLearning.database.model.paper.PaperListResponse
import com.tech4gen.eLearning.database.model.paper.PaperRequest
import com.tech4gen.eLearning.database.model.paper.PaperResponse
import com.tech4gen.eLearning.database.model.paper.PaperType
import com.tech4gen.eLearning.database.repository.PaperRepository
import com.tech4gen.eLearning.database.repository.SchoolRepository
import com.tech4gen.eLearning.database.repository.SubjectRepository
import com.tech4gen.eLearning.util.reqLogger
import com.tech4gen.eLearning.util.toJson
import org.apache.coyote.BadRequestException
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import kotlin.math.ceil

@Service
class PaperService(
    private val paperRepository: PaperRepository,
    private val subjectRepository: SubjectRepository,
    private val schoolRepository: SchoolRepository
) {

    fun createPaper(
        paperRequest: PaperRequest
    ): PaperResponse {
        reqLogger("paperCreateReq ${paperRequest.toJson()}")
        var schoolName: String = ""
        if (paperRequest.paperType >= 5) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid paper type"
            )
        }


        val subjectIdExist = subjectRepository.findById(ObjectId(paperRequest.subjectId))

        if (paperRequest.paperType == PaperType.FIRST_TERM_PAPER.value ||
            paperRequest.paperType == PaperType.SECOND_TERM_PAPER.value ||
            paperRequest.paperType == PaperType.THIRD_TERM_PAPER.value) {

            if (paperRequest.schoolId.isEmpty()) throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid school ID"
            )

            val schoolIdExist = schoolRepository.findById(ObjectId(paperRequest.schoolId))
            if (!schoolIdExist.isPresent) throw UsernameNotFoundException("School not found")

            schoolName = schoolIdExist.get().school
        }

        if (paperRequest.subjectId.length  != 24) throw BadRequestException("Invalid subject ID")
        if (!subjectIdExist.isPresent) throw UsernameNotFoundException("Subject not found")

        val paper = Paper(
            paperTitle = paperRequest.paperTitle,
            year = paperRequest.year,
            paperType = paperRequest.paperType,
            subjectId = paperRequest.subjectId,
            schoolId = paperRequest.schoolId
        )

        paperRepository.save(paper)
        return PaperResponse(
            id = paper.id.toString(),
            paperTitle = paper.paperTitle,
            subjectId = paper.subjectId,
            year = paper.year,
            paperType = paper.paperType,
            subjectName = subjectIdExist.get().subjectName,
            schoolId = paper.schoolId,
            schoolName = schoolName,
            createdAt = paper.createdAt.toString()
        )
    }

    fun getPaper(
        page: Int,
        size: Int
    ): PaperListResponse {
        val allPapers = paperRepository.findAll()

        val totalPapers = allPapers.size.toLong() // Get the total count of papers

        val totalPages = if (size > 0) ceil(totalPapers.toDouble() / size).toInt() else 0

        val startIndex = (page - 1) * size
        val endIndex = (startIndex + size).coerceAtMost(allPapers.size) // Ensure endIndex doesn't go out of bounds

        val paginatedPapers = if (startIndex < endIndex) {
            allPapers.subList(startIndex, endIndex)
        } else {
            emptyList()
        }

        val paper =  paginatedPapers.map { paper ->
            PaperResponse(
                id = paper.id.toString(),
                paperTitle = paper.paperTitle,
                subjectId = paper.subjectId,
                year = paper.year,
                paperType = paper.paperType,
                subjectName = "",
                schoolId = paper.schoolId,
                schoolName = "",
                createdAt = paper.createdAt.toString()
            )
        }

        if (paper.isEmpty()) {
            throw UsernameNotFoundException(
                "No papers found"
            )
        }

        return PaperListResponse(
            message = "Paper list retrieved successfully",
            papers = paper,
            currentPage = page,
            pageSize = size,
            totalPages = totalPages
        )
    }
}