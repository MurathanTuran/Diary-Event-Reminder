package com.turanapps.eventreminder.Backend.Controller.Abstract

import com.turanapps.eventreminder.DTO.Request.EventRequest
import com.turanapps.eventreminder.DTO.Response.EventResponse
import java.util.*

interface EventController {

    suspend fun getAllEvents(): List<EventResponse>

    suspend fun getEventById(id: Int): EventResponse

    suspend fun getEventByDate(date: Date): List<EventResponse>

    suspend fun insertEvent(eventRequest: EventRequest): Int

    suspend fun updateEvent(eventRequest: EventRequest)

    suspend fun deleteEvent(eventRequest: EventRequest)

    suspend fun deletePassedEvents()

}