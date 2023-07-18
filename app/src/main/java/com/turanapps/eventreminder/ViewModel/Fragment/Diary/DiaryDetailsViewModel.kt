package com.turanapps.eventreminder.ViewModel.Fragment.Diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.turanapps.eventreminder.Backend.Controller.Concrete.DiaryControllerImpl
import com.turanapps.eventreminder.DTO.Request.DiaryRequest
import com.turanapps.eventreminder.Error.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DiaryDetailsViewModel @Inject constructor(
    private val diaryController: DiaryControllerImpl
    ) : ViewModel() {

    private var emptyDiaryHeaderErrorB = MutableLiveData<Boolean?>()
    private val emptyDiaryHeaderError = "Enter Header"
    val emptyDiaryHeaderErrorEntity = Error(emptyDiaryHeaderErrorB, emptyDiaryHeaderError)

    private var emptyDiaryCommentErrorB = MutableLiveData<Boolean?>()
    private val emptyDiaryCommentError = "Enter Comment"
    val emptyDiaryCommentErrorEntity = Error(emptyDiaryCommentErrorB, emptyDiaryCommentError)

    private var emptyDiaryIdErrorB = MutableLiveData<Boolean?>()
    private val emptyDiaryIdError = "Id Null"
    val emptyDiaryIdErrorEntity = Error(emptyDiaryIdErrorB, emptyDiaryIdError)

    /*
    private var emptyDiaryDateErrorB = MutableLiveData<Boolean?>()
    private val emptyDiaryDateError = "Select Date"
    val emptyDiaryDateErrorEntity = Error(emptyDiaryDateErrorB, emptyDiaryDateError)
     */

    private var insertDiaryErrorB = MutableLiveData<Boolean?>()
    private val insertDiaryError = "Error While Inserting Diary to Room Database"
    val insertDiaryErrorEntity = Error(insertDiaryErrorB, insertDiaryError)

    private var deleteDiaryErrorB = MutableLiveData<Boolean?>()
    private val deleteDiaryError = "Error While Deleting Event from Room Database"
    val deleteDiaryErrorEntity = Error(deleteDiaryErrorB, deleteDiaryError)

    private var updateDiaryErrorB = MutableLiveData<Boolean?>()
    private val updateDiaryError = "Error While Updating Event from Room Database"
    val updateDiaryErrorEntity = Error(updateDiaryErrorB, updateDiaryError)

    private var _readyToGoDiaryFragmentB = MutableLiveData<Boolean?>(false)
    val readyToGoDiaryFragmentB: LiveData<Boolean?> = _readyToGoDiaryFragmentB

    fun insertDiary(diaryRequest: DiaryRequest){

        try {
            runBlocking {
                if(!emptyArea(diaryRequest)){
                    val deferredResult = async(Dispatchers.IO) {
                        diaryController.insertDiary(diaryRequest)
                    }
                    deferredResult.await()
                    _readyToGoDiaryFragmentB.value = true
                }
            }
        }catch (e: Exception){
            insertDiaryErrorB.value = true
        }

    }

    fun deleteDiary(diaryRequest: DiaryRequest){

        try {
            runBlocking {
                if(!emptyArea(diaryRequest) && !isIdEmpty(diaryRequest.id)){
                    val deferredResult = async(Dispatchers.IO) {
                        diaryController.deleteDiary(diaryRequest)
                    }
                    deferredResult.await()
                    _readyToGoDiaryFragmentB.value = true
                }
            }
        }catch (e: Exception){
            deleteDiaryErrorB.value = true
        }

    }

    fun updateDiary(diaryRequest: DiaryRequest){

        try {
            runBlocking {
                if(!emptyArea(diaryRequest) && !isIdEmpty(diaryRequest.id)){
                    val deferredResult = async(Dispatchers.IO) {
                        diaryController.updateDiary(diaryRequest)
                    }
                    deferredResult.await()
                    _readyToGoDiaryFragmentB.value = true
                }
            }
        }catch (e: Exception){
            updateDiaryErrorB.value = true
        }

    }

    private fun emptyArea(diaryRequest: DiaryRequest): Boolean{

        var emptyArea = false

        if(isHeaderEmpty(diaryRequest.header)){
            emptyDiaryHeaderErrorB.value = true
            emptyArea = true
        }

        if(isCommentEmpty(diaryRequest.comment)){
            emptyDiaryCommentErrorB.value = true
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