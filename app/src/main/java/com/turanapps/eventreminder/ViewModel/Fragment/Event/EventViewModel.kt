package com.turanapps.eventreminder.ViewModel.Fragment.Event

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turanapps.eventreminder.Backend.Controller.Concrete.EventControllerImpl
import com.turanapps.eventreminder.DTO.Response.EventResponse
import com.turanapps.eventreminder.Error.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventController: EventControllerImpl
    ): ViewModel() {

    private var getEventsErrorB = MutableLiveData<Boolean?>()
    private val getEventsError = "Error While Getting Events from Room Database"
    val getEventsErrorEntity = Error(getEventsErrorB, getEventsError)

    private var deleteEventsErrorB = MutableLiveData<Boolean?>()
    private val deleteEventsError = "Error While Deleting Events from Room Database"
    val deleteEventsErrorEntity = Error(deleteEventsErrorB, deleteEventsError)

    private val _refreshState = MutableLiveData<Boolean>()
    val refreshState: LiveData<Boolean>
        get() = _refreshState

    fun getAllEvents(): ArrayList<EventResponse>{

        var eventList = ArrayList<EventResponse>()

        try {
            runBlocking {
                val deferredResult = async(Dispatchers.IO) {
                    eventList = eventController.getAllEvents() as ArrayList<EventResponse>
                }
                deferredResult.await()
            }
        }catch (e: Exception){
            getEventsErrorB.value = true
        }

        return eventList

    }

    fun deletePassedEvents(){
        try {
            runBlocking {
                val deferredResult = async(Dispatchers.IO) {
                    eventController.deletePassedEvents()
                }
                deferredResult.await()
            }
        }catch (e: Exception){
            deleteEventsErrorB.value = true
        }

    }

    fun performRefresh() {
        viewModelScope.launch {
            _refreshState.value = true
            deletePassedEvents()
            _refreshState.value = false
        }
    }

}