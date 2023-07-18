package com.turanapps.eventreminder.Backend.Service.Concrete

import com.turanapps.eventreminder.Backend.Database.Repository
import com.turanapps.eventreminder.Backend.Service.Abstract.EventService
import com.turanapps.eventreminder.DTO.Request.EventRequest
import com.turanapps.eventreminder.DTO.Response.EventResponse
import com.turanapps.eventreminder.Model.Event
import java.util.*
import javax.inject.Inject

class EventServiceImpl @Inject constructor(
    private val repository: Repository
): EventService {

    private val dao = repository.getEventDao()

    override suspend fun getAllEvents(): List<EventResponse> {

        val eventList = dao.getAllEvents()

        val eventResponseList: List<EventResponse> = eventList.map { event -> EventResponse(id = event.id, header = event.header, comment = event.comment, date = event.date) }

        return eventResponseList

    }

    override suspend fun getEventById(id: Int): EventResponse {

        val event = dao.getEventById(id)

        val eventResponse = EventResponse(id = event.id, header = event.header, comment = event.comment, date = event.date)

        return eventResponse

    }

    override suspend fun getEventsByDate(date: Date): List<EventResponse> {

        val startDate = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        val endDate = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.time

        val eventList = dao.getEventsByDate(startDate, endDate)

        val eventResponseList: List<EventResponse> = eventList.map { event -> EventResponse(id = event.id, header = event.header, comment = event.comment, date = event.date) }

        return eventResponseList

    }

    override suspend fun insertEvent(eventRequest: EventRequest): Int {
        return dao.insertEvent(Event(header = eventRequest.header, comment = eventRequest.comment, date = eventRequest.date)).toInt()
    }

    override suspend fun updateEvent(eventRequest: EventRequest) {
        dao.updateEvent(Event(id = eventRequest.id!!, header = eventRequest.header, comment = eventRequest.comment, date = eventRequest.date))
    }

    override suspend fun deleteEvent(eventRequest: EventRequest) {
        dao.deleteEvent(Event(id = eventRequest.id!!, header = eventRequest.header, comment = eventRequest.comment, date = eventRequest.date))
    }

    override suspend fun deletePassedEvents(currentDate: Date) {
        dao.deletePassedEvents(currentDate)
    }

}