package com.company.shared.event

import java.time.LocalDateTime
import java.util.UUID

abstract class BaseEvent(
    val eventId: String = UUID.randomUUID().toString(),
    val eventTimestamp: LocalDateTime = LocalDateTime.now(),
    val eventType: String
)
