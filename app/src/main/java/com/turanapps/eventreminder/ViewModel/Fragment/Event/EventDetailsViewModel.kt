package com.turanapps.eventreminder.ViewModel.Fragment.Event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.turanapps.eventreminder.Backend.Controller.Concrete.EventControllerImpl
import com.turanapps.eventreminder.DTO.Request.EventRequest
import com.turanapps.eventreminder.Error.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val eventController: EventControllerImpl
    ): ViewModel() {

    private var emptyEventHeaderErrorB = MutableLiveData<Boolean?>()
    private val emptyEventHeaderError = "Enter Header"
    val emptyEventHeaderErrorEntity = Error(emptyEventHeaderErrorB, emptyEventHeaderError)

    private var emptyEventCommentErrorB = MutableLiveData<Boolean?>()
    private val emptyEventCommentError = "Enter Comment"
    val emptyEventCommentErrorEntity = Error(emptyEventCommentErrorB, emptyEventCommentError)

    private var emptyEventIdErrorB = MutableLiveData<Boolean?>()
    private val emptyEventIdError = "Id Null"
    val emptyEventIdErrorEntity = Error(emptyEventIdErrorB, emptyEventIdError)

    /*
    private var emptyEventDateErrorB = MutableLiveData<Boolean?>()
    private val emptyEventDateError = "Select Date"
    val emptyEventDateErrorEntity = Error(emptyEventDateErrorB, emptyEventDateError)
     */

    private var insertEventErrorB = MutableLiveData<Boolean?>()
    private val insertEventError = "Error While Inserting Event to Room Database"
    val insertEventErrorEntity = Error(insertEventErrorB, insertEventError)

    private var deleteEventErrorB = MutableLiveData<Boolean?>()
    private val deleteEventError = "Error While Deleting Event from Room Database"
    val deleteEventErrorEntity = Error(deleteEventErrorB, deleteEventError)

    private var updateEventErrorB = MutableLiveData<Boolean?>()
    private val updateEventError = "Error While Updating Event from Room Database"
    val updateEventErrorEntity = Error(updateEventErrorB, updateEventError)

    private var _readyToGoEventFragmentB = MutableLiveData<Boolean>(false)
    val readyToGoEventFragmentB: LiveData<Boolean> = _readyToGoEventFragmentB

    private var id = 0

    fun insertEvent(eventRequest: EventRequest): Int{
        try {
            runBlocking {
                if(!emptyArea(eventRequest)){
                    val deferredResult = async(Dispatchers.IO) {
                        id = eventController.insertEvent(eventRequest)
                    }
                    deferredResult.await()
                    _readyToGoEventFragmentB.value = true
                }
            }
        }catch (e: Exception){
            insertEventErrorB.value = true
        }
        return id
    }

    fun deleteEvent(eventRequest: EventRequest){

        try {
            runBlocking {
                if(!emptyArea(eventRequest) && !isIdEmpty(eventRequest.id)){
                    val deferredResult = async(Dispatchers.IO) {
                        eventController.deleteEvent(eventRequest)
                    }
                    deferredResult.await()
                    _readyToGoEventFragmentB.value = true
                }
            }
        }catch (e: Exception){
            deleteEventErrorB.value = true
        }

    }

    fun updateEvent(eventRequest: EventRequest){

        try {
            runBlocking {
                if(!emptyArea(eventRequest) && !isIdEmpty(eventRequest.id)){
                    val deferredResult = async(Dispatchers.IO) {
                        eventController.updateEvent(eventRequest)
                    }
                    deferredResult.await()
                    _readyToGoEventFragmentB.value = true
                }
            }
        }catch (e: Exception){
            updateEventErrorB.value = true
        }

    }

    private fun emptyArea(eventRequest: EventRequest): Boolean{

        var emptyArea = false

        if(isHeaderEmpty(eventRequest.header)){
            emptyEventHeaderErrorB.value = true
            emptyArea = true
        }

        if(isCommentEmpty(eventRequest.comment)){
            emptyEventCommentErrorB.value = true
            emptyArea = true
        }

        return emptyArea

    }

    private fun isHeaderEmpty(header: String): Boolean{
        return header.isEmpty()
    }

    private fun isCommentEmpty(comment: String): Boolean{
        return comment.isEmpty()
    }

    private fun isIdEmpty(id: Int?): Boolean{
        return (id==null)
    }

}