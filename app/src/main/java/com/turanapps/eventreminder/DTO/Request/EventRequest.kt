package com.turanapps.eventreminder.DTO.Request

import java.util.*

data class EventRequest(
    val id: Int? = null,
    val header: String,
    val comment: String,
    val date: Date
)
