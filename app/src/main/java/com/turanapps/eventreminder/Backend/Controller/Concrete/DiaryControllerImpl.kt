package com.turanapps.eventreminder.Backend.Controller.Concrete

import com.turanapps.eventreminder.Backend.Controller.Abstract.DiaryController
import com.turanapps.eventreminder.Backend.Service.Concrete.DiaryServiceImpl
import com.turanapps.eventreminder.DTO.Request.DiaryRequest
import com.turanapps.eventreminder.DTO.Response.DiaryResponse
import java.util.*
import javax.inject.Inject

class DiaryControllerImpl @Inject constructor(
    private val diaryService: DiaryServiceImpl
    ): DiaryController {

    override suspend fun getAllDiaries(): List<DiaryResponse> {
        return diaryService.getAllDiaries()
    }

    override suspend fun getDiaryById(id: Int): DiaryResponse {
        return diaryService.getDiaryById(id)
    }

    override suspend fun getDiaryByDate(date: Date): List<DiaryResponse> {
        return diaryService.getDiariesByDate(date)
    }

    override suspend fun insertDiary(diaryRequest: DiaryRequest) {
        diaryService.insertDiary(diaryRequest)
    }

    override suspend fun updateDiary(diaryRequest: DiaryRequest) {
        diaryService.updateDiary(diaryRequest)
    }

    override suspend fun deleteDiary(diaryRequest: DiaryRequest) {
        diaryService.deleteDiary(diaryRequest)
    }

}