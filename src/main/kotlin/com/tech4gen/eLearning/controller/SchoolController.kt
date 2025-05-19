package com.tech4gen.eLearning.controller

import com.tech4gen.eLearning.database.model.school.SchoolListResponse
import com.tech4gen.eLearning.database.model.school.SchoolRequest
import com.tech4gen.eLearning.database.model.school.SchoolResponse
import com.tech4gen.eLearning.service.SchoolService
import com.tech4gen.eLearning.util.reqLogger
import com.tech4gen.eLearning.util.resLogger
import com.tech4gen.eLearning.util.toJson
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/school")
class SchoolController(
    private val schoolService: SchoolService
) {

    @PostMapping("/create")
    fun createSchool(
        @Valid @RequestBody schoolRequest: List<SchoolRequest>
    ): ResponseEntity<SchoolListResponse> {
        val sList = schoolService.addSchool(schoolRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(sList)
    }

    @GetMapping()
    fun getAllSchool(
        @RequestParam("name", required = false) schoolName: String? = null
    ): ResponseEntity<SchoolListResponse> {
        val sList = schoolService.getAllSchool(schoolName ?: "")
        return ResponseEntity.ok().body(sList)

    }

}