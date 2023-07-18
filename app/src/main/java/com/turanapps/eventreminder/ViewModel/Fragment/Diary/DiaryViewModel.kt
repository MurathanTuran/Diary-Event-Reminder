package com.turanapps.eventreminder.ViewModel.Fragment.Diary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.turanapps.eventreminder.Backend.Controller.Concrete.DiaryControllerImpl
import com.turanapps.eventreminder.DTO.Response.DiaryResponse
import com.turanapps.eventreminder.Error.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val diaryController: DiaryControllerImpl
    ): ViewModel() {

    private var getDiariesErrorB = MutableLiveData<Boolean?>()
    private val getDiariesError = "Error While Getting Diaries from Room Database"
    val getDiariesErrorEntity = Error(getDiariesErrorB, getDiariesError)

    fun getAllDiaries(): ArrayList<DiaryResponse>{

        var diaryList = ArrayList<DiaryResponse>()

        try {
            runBlocking {
                val deferredResult = async(Dispatchers.IO) {
                    diaryList = diaryController.getAllDiaries() as ArrayList<DiaryResponse>
                }
                deferredResult.await()
            }
        }catch (e: Exception){
            getDiariesErrorB.value = true
        }

        return diaryList

    }

}