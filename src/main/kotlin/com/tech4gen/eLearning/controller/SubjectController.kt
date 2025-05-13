package com.tech4gen.eLearning.controller

import com.tech4gen.eLearning.database.model.subject.SubjectListResponse
import com.tech4gen.eLearning.database.model.subject.SubjectRequest
import com.tech4gen.eLearning.database.model.subject.SubjectResponse
import com.tech4gen.eLearning.service.SubjectService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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
            streamId = request.streamId,
            imageUrl = request.imageUrl ?: ""
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping()
    fun getAllSubject(
        @RequestParam(required = true) streamId: String,
        @RequestParam(required = false) sub1: String?,
        @RequestParam(required = false) sub2: String?,
        @RequestParam(required = false) sub3: String?,
    ): ResponseEntity<SubjectListResponse> {

        val response = subjectService.getAllSubjects(
            streamId = streamId,
            subject1 = sub1?.trim() ?: "",
            subject2 = sub2?.trim() ?: "",
            subject3 = sub3?.trim() ?: "",
        )
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }
}