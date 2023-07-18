package com.turanapps.eventreminder.ViewModel.Fragment.Calendar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.turanapps.eventreminder.Backend.Controller.Concrete.DiaryControllerImpl
import com.turanapps.eventreminder.Backend.Controller.Concrete.EventControllerImpl
import com.turanapps.eventreminder.DTO.Response.DiaryResponse
import com.turanapps.eventreminder.DTO.Response.EventResponse
import com.turanapps.eventreminder.Error.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val diaryController: DiaryControllerImpl,
    private val eventController: EventControllerImpl
) : ViewModel() {

    private var getEventsByDateErrorB = MutableLiveData<Boolean?>()
    private val getEventsByDateError = "Error While Getting Events from Room Database"
    val getEventsByDateErrorEntity = Error(getEventsByDateErrorB, getEventsByDateError)

    private var getDiariesByDateErrorB = MutableLiveData<Boolean?>()
    private val getDiariesByDateError = "Error While Getting Diaries from Room Database"
    val getDiariesByDateErrorEntity = Error(getDiariesByDateErrorB, getDiariesByDateError)

    fun getEventsByDate(date: Date): ArrayList<EventResponse>{

        var eventList = ArrayList<EventResponse>()

        try {
            runBlocking {
                val deferredResult = async(Dispatchers.IO) {
                    eventList = eventController.getEventByDate(date) as ArrayList<EventResponse>
                }
                deferredResult.await()
            }
        }catch (e: Exception){
            getEventsByDateErrorB.value = true
        }

        return eventList

    }

    fun getDiariesByDate(date: Date): ArrayList<DiaryResponse>{

        var diaryList = ArrayList<DiaryResponse>()

        try {
            runBlocking {
                val deferredResult = async(Dispatchers.IO) {
                    diaryList = diaryController.getDiaryByDate(date) as ArrayList<DiaryResponse>
                }
                deferredResult.await()
            }
        }catch (e: Exception){
            getDiariesByDateErrorB.value = true
        }

        return diaryList

    }

}