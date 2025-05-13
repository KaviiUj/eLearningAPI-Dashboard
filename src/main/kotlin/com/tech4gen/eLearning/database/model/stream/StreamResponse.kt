package com.tech4gen.eLearning.database.model.stream

data class StreamResponse(
    val streamName: String,
    val streamId: String,
    val message: String,
    val imageUrl: String? = null,
    val createdAt: String
)

data class StreamListResponse(
    val message: String,
    val streams: List<StreamResponse>
)
