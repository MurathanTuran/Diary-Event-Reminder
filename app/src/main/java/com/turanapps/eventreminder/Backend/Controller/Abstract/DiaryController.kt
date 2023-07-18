package com.turanapps.eventreminder.Backend.Controller.Abstract

import com.turanapps.eventreminder.DTO.Request.DiaryRequest
import com.turanapps.eventreminder.DTO.Response.DiaryResponse
import java.util.*

interface DiaryController {

    suspend fun getAllDiaries(): List<DiaryResponse>

    suspend fun getDiaryById(id: Int): DiaryResponse

    suspend fun getDiaryByDate(date: Date): List<DiaryResponse>

    suspend fun insertDiary(diaryRequest: DiaryRequest)

    suspend fun updateDiary(diaryRequest: DiaryRequest)

    suspend fun deleteDiary(diaryRequest: DiaryRequest)

}