package com.tech4gen.eLearning.controller

import com.tech4gen.eLearning.database.model.question.QuestionRequest
import com.tech4gen.eLearning.database.model.question.QuestionResponse
import com.tech4gen.eLearning.service.QuestionService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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
}