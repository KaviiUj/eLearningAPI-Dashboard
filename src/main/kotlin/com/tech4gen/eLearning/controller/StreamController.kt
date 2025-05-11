package com.tech4gen.eLearning.controller

import com.tech4gen.eLearning.database.model.stream.StreamRequest
import com.tech4gen.eLearning.database.model.stream.StreamResponse
import com.tech4gen.eLearning.service.StreamService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/stream")
class StreamController(
    private val streamService: StreamService
) {

    @PostMapping("/create")
    fun createStream(
        @Valid @RequestBody streamRequest: StreamRequest,
    ): ResponseEntity<StreamResponse> {

        val response = streamService.createStream(
            streamName = streamRequest.streamName,
            userId = streamRequest.userId
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}