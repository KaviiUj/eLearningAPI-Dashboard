package com.tech4gen.eLearning.controller

import com.tech4gen.eLearning.database.model.paper.PaperListResponse
import com.tech4gen.eLearning.database.model.paper.PaperRequest
import com.tech4gen.eLearning.database.model.paper.PaperResponse
import com.tech4gen.eLearning.service.PaperService
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
@RequestMapping("/api/paper")
class PaperController(
    private val paperService: PaperService
) {

    @PostMapping("/create")
    fun createPaper(
        @Valid @RequestBody paperRequest: PaperRequest
    ): ResponseEntity<PaperResponse> {
        val response = paperService.createPaper(paperRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

   @GetMapping()
    fun getPaper(
        @RequestParam(name = "page") page: Int,
        @RequestParam(name = "size") size: Int,
    ): ResponseEntity<PaperListResponse> {
        val response = paperService.getPaper(page,size)
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

}