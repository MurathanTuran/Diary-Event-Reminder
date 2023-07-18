package com.turanapps.eventreminder.Backend.Database.RoomDB.Dao

import androidx.room.*
import com.turanapps.eventreminder.CONST.Const
import com.turanapps.eventreminder.CONST.Const.DIARY_TABLE_NAME
import com.turanapps.eventreminder.Model.Diary
import com.turanapps.eventreminder.Model.Event
import java.util.*

@Dao
interface DiaryDao {

    @Query("SELECT * FROM ${DIARY_TABLE_NAME} ORDER BY date ASC")
    suspend fun getAllDiaries(): List<Diary>

    @Query("SELECT * FROM ${DIARY_TABLE_NAME} WHERE id = :id")
    suspend fun getDiaryById(id: Int): Diary

    @Query("SELECT * FROM ${DIARY_TABLE_NAME} WHERE date >= :startDate AND date < :endDate")
    suspend fun getDiariesByDate(startDate: Date, endDate: Date): List<Diary>

    @Insert
    suspend fun insertDiary(diary: Diary)

    @Update
    suspend fun updateDiary(diary: Diary)

    @Delete
    suspend fun deleteDiary(diary: Diary)

}