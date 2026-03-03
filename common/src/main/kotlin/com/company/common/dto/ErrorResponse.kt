package com.company.common.dto

import java.time.LocalDateTime

data class ErrorResponse(
    val status: Int,
    val message: String,
    val errors: List<String> = emptyList(),
    val path: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
