package com.company.common.event

class NotificationEvent(
    val recipientId: String,
    val channel: com.company.common.event.NotificationChannel,
    val subject: String,
    val body: String
) : com.company.common.event.BaseEvent(eventType = "NOTIFICATION")
