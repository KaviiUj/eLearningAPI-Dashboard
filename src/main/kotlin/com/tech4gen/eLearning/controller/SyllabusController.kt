package com.tech4gen.eLearning.controller

import com.tech4gen.eLearning.database.model.syllabus.SyllabusListResponse
import com.tech4gen.eLearning.database.model.syllabus.SyllabusRequest
import com.tech4gen.eLearning.service.SyllabusService
import com.tech4gen.eLearning.util.reqLogger
import com.tech4gen.eLearning.util.toJson
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/syllabus")
class SyllabusController(
    private val syllabusService: SyllabusService
) {

    @PostMapping("/create")
    fun createSyllabus(
      @RequestBody syllabusRequest: List<SyllabusRequest>
    ): ResponseEntity<SyllabusListResponse> {
        reqLogger("Syllabus request: ${syllabusRequest.toJson()}")
        val response = syllabusService.createSyllabus(syllabusList = syllabusRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}