package com.company.common.event

import java.math.BigDecimal

class OrderCreatedEvent(
    val orderId: String,
    val customerId: String,
    val totalAmount: BigDecimal
) : com.company.common.event.BaseEvent(eventType = "ORDER_CREATED")
