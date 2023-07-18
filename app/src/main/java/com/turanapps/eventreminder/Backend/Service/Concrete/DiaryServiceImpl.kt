package com.turanapps.eventreminder.Backend.Service.Concrete

import com.turanapps.eventreminder.Backend.Database.Repository
import com.turanapps.eventreminder.Backend.Service.Abstract.DiaryService
import com.turanapps.eventreminder.DTO.Request.DiaryRequest
import com.turanapps.eventreminder.DTO.Response.DiaryResponse
import com.turanapps.eventreminder.Model.Diary
import java.util.*
import javax.inject.Inject

class DiaryServiceImpl @Inject constructor(
    private val repository: Repository
): DiaryService {

    private val dao = repository.getDiaryDao()

    override suspend fun getAllDiaries(): List<DiaryResponse> {

        val diaryList = dao.getAllDiaries()

        val diaryResponseList: List<DiaryResponse> = diaryList.map { diary -> DiaryResponse(id = diary.id, header = diary.header, comment = diary.comment, date = diary.date) }

        return diaryResponseList

    }

    override suspend fun getDiaryById(id: Int): DiaryResponse {

        val diary = dao.getDiaryById(id)

        val diaryResponse = DiaryResponse(id = diary.id, header = diary.header, comment = diary.comment, date = diary.date)

        return diaryResponse

    }

    override suspend fun getDiariesByDate(date: Date): List<DiaryResponse> {

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

        val diaryList = dao.getDiariesByDate(startDate, endDate)

        val diaryResponseList: List<DiaryResponse> = diaryList.map { diary -> DiaryResponse(id = diary.id, header = diary.header, comment = diary.comment, date = diary.date) }

        return diaryResponseList

    }

    override suspend fun insertDiary(diaryRequest: DiaryRequest) {
        dao.insertDiary(Diary(header = diaryRequest.header, comment = diaryRequest.comment, date = diaryRequest.date))
    }

    override suspend fun updateDiary(diaryRequest: DiaryRequest) {
        dao.updateDiary(Diary(id = diaryRequest.id!!, header = diaryRequest.header, comment = diaryRequest.comment, date = diaryRequest.date))
    }

    override suspend fun deleteDiary(diaryRequest: DiaryRequest) {
        dao.deleteDiary(Diary(id = diaryRequest.id!!, header = diaryRequest.header, comment = diaryRequest.comment, date = diaryRequest.date))
    }

}