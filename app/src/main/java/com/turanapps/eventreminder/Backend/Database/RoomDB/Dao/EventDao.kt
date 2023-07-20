package com.turanapps.eventreminder.Backend.Database.RoomDB.Dao

import androidx.room.*
import com.turanapps.eventreminder.CONST.Const.EVENT_TABLE_NAME
import com.turanapps.eventreminder.Model.Event
import java.util.*

@Dao
interface EventDao {

    @Query("SELECT * FROM ${EVENT_TABLE_NAME} ORDER BY date ASC")
    suspend fun getAllEvents(): List<Event>

    @Query("SELECT * FROM ${EVENT_TABLE_NAME} WHERE id = :id")
    suspend fun getEventById(id: Int): Event

    @Query("SELECT * FROM ${EVENT_TABLE_NAME} WHERE date >= :startDate AND date < :endDate")
    suspend fun getEventsByDate(startDate: Date, endDate: Date): List<Event>

    @Insert
    suspend fun insertEvent(event: Event): Long

    @Update
    suspend fun updateEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("DELETE FROM ${EVENT_TABLE_NAME} WHERE date < :currentDate")
    suspend fun deletePassedEvents(currentDate: Date)

}