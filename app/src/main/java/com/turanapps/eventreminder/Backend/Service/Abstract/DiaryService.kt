package com.turanapps.eventreminder.Backend.Service.Abstract

import com.turanapps.eventreminder.DTO.Request.DiaryRequest
import com.turanapps.eventreminder.DTO.Response.DiaryResponse
import com.turanapps.eventreminder.DTO.Response.EventResponse
import com.turanapps.eventreminder.Model.Diary
import java.util.*

interface DiaryService {

    suspend fun getAllDiaries(): List<DiaryResponse>

    suspend fun getDiaryById(id: Int): DiaryResponse

    suspend fun getDiariesByDate(date: Date): List<DiaryResponse>

    suspend fun insertDiary(diaryRequest: DiaryRequest)

    suspend fun updateDiary(diaryRequest: DiaryRequest)

    suspend fun deleteDiary(diaryRequest: DiaryRequest)

}