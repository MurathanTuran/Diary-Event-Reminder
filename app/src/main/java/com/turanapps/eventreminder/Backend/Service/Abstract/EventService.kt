package com.turanapps.eventreminder.Backend.Service.Abstract

import com.turanapps.eventreminder.DTO.Request.EventRequest
import com.turanapps.eventreminder.DTO.Response.EventResponse
import java.util.*

interface EventService {

    suspend fun getAllEvents(): List<EventResponse>

    suspend fun getEventById(id: Int): EventResponse

    suspend fun getEventsByDate(date: Date): List<EventResponse>

    suspend fun insertEvent(eventRequest: EventRequest): Int

    suspend fun updateEvent(eventRequest: EventRequest)

    suspend fun deleteEvent(eventRequest: EventRequest)

    suspend fun deletePassedEvents(currentDate: Date)

}