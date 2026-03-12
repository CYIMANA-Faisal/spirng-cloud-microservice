package com.company.shared.event

class NotificationEvent(
    val recipientId: String,
    val channel: NotificationChannel,
    val subject: String,
    val body: String
) : BaseEvent(eventType = "NOTIFICATION")
