package com.tech4gen.eLearning.controller

import com.tech4gen.eLearning.database.model.stream.StreamListResponse
import com.tech4gen.eLearning.database.model.stream.StreamRequest
import com.tech4gen.eLearning.database.model.stream.StreamResponse
import com.tech4gen.eLearning.service.StreamService
import jakarta.validation.Valid
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

    @GetMapping()
    fun getAllStreams(): ResponseEntity<StreamListResponse> {
        val response = streamService.getAllStreams()
        return ResponseEntity.ok(response)
    }

    @PutMapping()
    fun updateStream(
        @Valid @RequestBody streamRequest: StreamRequest,
        @RequestParam(required = true) streamId: String
    ): ResponseEntity<StreamResponse> {
        val response = streamService.updateStream(
            streamId = streamId,
            userId = streamRequest.userId,
            streamName = streamRequest.streamName
        )

        return ResponseEntity.ok(response)
    }

    @DeleteMapping()
    fun deleteStream(
        @RequestParam(required = true) streamId: String
    ): ResponseEntity<StreamResponse> {
        val response = streamService.deleteStream(streamId)
        return ResponseEntity.ok(response)
    }
}