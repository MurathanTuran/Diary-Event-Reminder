package com.turanapps.eventreminder.Backend.Controller.Concrete

import com.turanapps.eventreminder.Backend.Controller.Abstract.EventController
import com.turanapps.eventreminder.Backend.Service.Concrete.EventServiceImpl
import com.turanapps.eventreminder.DTO.Request.EventRequest
import com.turanapps.eventreminder.DTO.Response.EventResponse
import java.util.*
import javax.inject.Inject

class EventControllerImpl @Inject constructor(
    private val eventService: EventServiceImpl
    ): EventController{

    override suspend fun getAllEvents(): List<EventResponse> {
        return eventService.getAllEvents()
    }

    override suspend fun getEventById(id: Int): EventResponse {
        return eventService.getEventById(id)
    }

    override suspend fun getEventByDate(date: Date): List<EventResponse> {
        return eventService.getEventsByDate(date)
    }

    override suspend fun insertEvent(eventRequest: EventRequest): Int {
        return eventService.insertEvent(eventRequest)
    }

    override suspend fun updateEvent(eventRequest: EventRequest) {
        eventService.updateEvent(eventRequest)
    }

    override suspend fun deleteEvent(eventRequest: EventRequest) {
        eventService.deleteEvent(eventRequest)
    }

    override suspend fun deletePassedEvents() {
        val currentDate = Date()
        eventService.deletePassedEvents(currentDate)
    }

}