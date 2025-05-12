package com.tech4gen.eLearning.controller

import com.tech4gen.eLearning.database.model.subject.SubjectListResponse
import com.tech4gen.eLearning.database.model.subject.SubjectRequest
import com.tech4gen.eLearning.database.model.subject.SubjectResponse
import com.tech4gen.eLearning.service.SubjectService
import com.tech4gen.eLearning.util.Loggable
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
): Loggable {

    @PostMapping("/create")
    fun createSubject(
        @Valid @RequestBody request: SubjectRequest
    ) : ResponseEntity<SubjectResponse>{
        logger.info("Create subject request: $request")
        val response = subjectService.createSubject(
            subjectName = request.name,
            streamId = request.streamId
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping()
    fun getAllSubject(
        @RequestParam(required = true) streamId: String
    ): ResponseEntity<SubjectListResponse> {
        logger.info("Get all subjects request: $streamId")
        val response = subjectService.getAllSubjects(
            streamId = streamId
        )
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }
}