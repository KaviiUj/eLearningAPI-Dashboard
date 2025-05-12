package com.tech4gen.eLearning.controller

import com.tech4gen.eLearning.database.model.subject.SubjectRequest
import com.tech4gen.eLearning.database.model.subject.SubjectResponse
import com.tech4gen.eLearning.service.SubjectService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/subject")
class SubjectController(
    private val subjectService: SubjectService
) {

    @PostMapping("/create")
    fun createSubject(
        @Valid @RequestBody request: SubjectRequest
    ) : ResponseEntity<SubjectResponse>{
        val response = subjectService.createSubject(
            subjectName = request.name,
            streamId = request.streamId
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}