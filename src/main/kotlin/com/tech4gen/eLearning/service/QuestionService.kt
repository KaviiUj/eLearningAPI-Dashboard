package com.tech4gen.eLearning.service

import com.tech4gen.eLearning.database.document.Question
import com.tech4gen.eLearning.database.model.paper.PaperType
import com.tech4gen.eLearning.database.model.question.QuestionRequest
import com.tech4gen.eLearning.database.model.question.QuestionResponse
import com.tech4gen.eLearning.database.repository.PaperRepository
import com.tech4gen.eLearning.database.repository.QuestionRepository
import com.tech4gen.eLearning.database.repository.SchoolRepository
import com.tech4gen.eLearning.database.repository.SubjectRepository
import org.apache.coyote.BadRequestException
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class QuestionService(
    private val subjectRepository: SubjectRepository,
    private val questionRepository: QuestionRepository,
    private val paperRepository: PaperRepository
) {

    fun createQuestion(
        questionRequest: QuestionRequest
    ): QuestionResponse {

        if (questionRequest.paperId.length != 24) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid id format for paper ID"
            )
        }
        val findPaperExist = paperRepository.findById(ObjectId(questionRequest.paperId))
        if (!findPaperExist.isPresent) {
            throw UsernameNotFoundException("Paper not found")
        }

        if (questionRequest.paperType >= 5) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid paper type"
            )
        }

        if (questionRequest.paperType == PaperType.FIRST_TERM_PAPER.value ||
            questionRequest.paperType == PaperType.SECOND_TERM_PAPER.value ||
            questionRequest.paperType == PaperType.THIRD_TERM_PAPER.value) {
            if (questionRequest.schoolId.isEmpty()) throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid school ID"
            )
        }

        if (questionRequest.subjectId.length  != 24) {
            throw  ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid subject ID"
            )
        }
        val subjectIdExist = subjectRepository.findById(ObjectId(questionRequest.subjectId))
        if (!subjectIdExist.isPresent) throw UsernameNotFoundException("Subject not found")

        val question = Question(
            questionText = questionRequest.questionText,
            answers = questionRequest.answers,
            correctAnswer = questionRequest.correctAnswer,
            subjectId = questionRequest.subjectId,
            subjectName = questionRequest.subjectName,
            schoolId = questionRequest.schoolId,
            schoolName = questionRequest.schoolName,
            year = questionRequest.year,
            paperType = questionRequest.paperType,
            syllabusId = questionRequest.syllabusId,
            syllabusName = questionRequest.syllabusName,
            questionUrl = questionRequest.questionUrl,
            paperId = questionRequest.paperId
        )

        questionRepository.save(question)

        return QuestionResponse(
            questionId = question.id.toString(),
            message = "Question created successfully",
            createdAt = question.createdAt.toString()
        )
    }
}