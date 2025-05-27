package com.tech4gen.eLearning.service

import com.tech4gen.eLearning.database.document.Question
import com.tech4gen.eLearning.database.model.paper.PaperType
import com.tech4gen.eLearning.database.model.question.QuestionListResponse
import com.tech4gen.eLearning.database.model.question.QuestionRequest
import com.tech4gen.eLearning.database.model.question.QuestionResponse
import com.tech4gen.eLearning.database.model.question.QuestionUpdateRequest
import com.tech4gen.eLearning.database.model.question.QuestionsResponse
import com.tech4gen.eLearning.database.repository.PaperRepository
import com.tech4gen.eLearning.database.repository.QuestionRepository
import com.tech4gen.eLearning.database.repository.SchoolRepository
import com.tech4gen.eLearning.database.repository.SubjectRepository
import com.tech4gen.eLearning.util.reqLogger
import org.apache.coyote.BadRequestException
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.Instant

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

    fun updateQuestion(
        questionUpdateRequest: QuestionUpdateRequest
    ): QuestionResponse {

        if (questionUpdateRequest.questionId.length != 24) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid question ID format"
            )
        }

        val questionExist = questionRepository.findById(ObjectId(questionUpdateRequest.questionId))
            .orElseThrow { UsernameNotFoundException("Question not found") }
        if (questionUpdateRequest.paperType >= 5) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid paper type"
            )
        }

        val question = questionExist.copy(
            questionText = questionUpdateRequest.questionText,
            answers = questionUpdateRequest.answers,
            correctAnswer = questionUpdateRequest.correctAnswer,
            paperType = questionUpdateRequest.paperType,
            syllabusId = questionUpdateRequest.syllabusId,
            syllabusName = questionUpdateRequest.syllabusName,
            questionUrl = questionUpdateRequest.questionUrl
        )

        questionRepository.save(question)

        return QuestionResponse(
            questionId = questionExist.id.toString(),
            message = "Question updated successfully",
            createdAt = questionExist.createdAt.toString(),
            updatedAt = Instant.now().toString()
        )
    }

    fun getQuestions(
        paperId: String
    ): QuestionListResponse {

        if (paperId.length != 24 || !ObjectId.isValid(paperId)) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid paper type"
            )
        }

        paperRepository.findById(ObjectId(paperId)).orElseThrow { UsernameNotFoundException("Paper not found") }
        val questions = questionRepository.findByPaperId(paperId)
        if (questions.isEmpty()) {
            throw UsernameNotFoundException("No questions found for the given paper ID")
        }

        return questions.toQuestionsResponse()
    }

    private fun List<Question>.toQuestionsResponse(): QuestionListResponse {
        val questions = this.map { question ->
            QuestionsResponse(
                questionId = question.id.toString(),
                paperId = question.paperId,
                questionText = question.questionText,
                questionUrl = question.questionUrl,
                answers = question.answers,
                correctAnswer = question.correctAnswer,
                subjectId = question.subjectId,
                subjectName = question.subjectName,
                schoolId = question.schoolId,
                schoolName = question.schoolName,
                year = question.year,
                paperType = question.paperType,
                syllabusId = question.syllabusId,
                syllabusName = question.syllabusName
            )
        }
        return QuestionListResponse(
            message = "Questions retrieved successfully",
            questions = questions
        )
    }

    fun deleteQuestion(
        questionId: String
    ): QuestionResponse {

        if (questionId.length != 24 || !ObjectId.isValid(questionId)) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid question id"
            )
        }

        val isFind = questionRepository.findById(ObjectId(questionId)).orElseThrow { UsernameNotFoundException("Question not found") }
        questionRepository.delete(isFind)

        return QuestionResponse(
            questionId = questionId,
            message = "Question deleted successfully",
            createdAt = isFind.createdAt.toString(),
            updatedAt = Instant.now().toString()
        )
    }
}