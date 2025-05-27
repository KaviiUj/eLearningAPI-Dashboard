package com.tech4gen.eLearning.controller

import com.tech4gen.eLearning.database.model.question.QuestionListResponse
import com.tech4gen.eLearning.database.model.question.QuestionRequest
import com.tech4gen.eLearning.database.model.question.QuestionResponse
import com.tech4gen.eLearning.database.model.question.QuestionUpdateRequest
import com.tech4gen.eLearning.service.QuestionService
import jakarta.validation.Valid
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/question")
class QuestionController(
    private val questionService: QuestionService
) {

    @PostMapping("/create")
    fun createQuestion(
        @Valid @RequestBody questionRequest: QuestionRequest
    ): ResponseEntity<QuestionResponse> {
        val response = questionService.createQuestion(questionRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PutMapping("/update")
    fun updateQuestion(
        @Valid @RequestBody questionUpdateRequest: QuestionUpdateRequest
    ): ResponseEntity<QuestionResponse> {
        val response = questionService.updateQuestion(questionUpdateRequest)
        return ResponseEntity.ok(response)
    }

    @GetMapping()
    fun getQuestions(
        @RequestParam("paperId", required = true) paperId: String,
    ): ResponseEntity<QuestionListResponse> {
        val response = questionService.getQuestions(paperId)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping()
    fun deleteQuestion(
        @RequestParam("questionId", required = true) questionId: String
    ): ResponseEntity<QuestionResponse> {
        val response = questionService.deleteQuestion(questionId)
        return ResponseEntity.ok().body(response)
    }
}