package com.turanapps.eventreminder.View.Fragment.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.turanapps.eventreminder.DTO.Request.DiaryRequest
import com.turanapps.eventreminder.DTO.Response.DiaryResponse
import com.turanapps.eventreminder.Error.ObserveErrors
import com.turanapps.eventreminder.ViewModel.Fragment.Diary.DiaryDetailsViewModel
import com.turanapps.eventreminder.databinding.FragmentDiaryDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DiaryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentDiaryDetailsBinding

    private val viewModel: DiaryDetailsViewModel by viewModels()

    private val calendar = Calendar.getInstance()

    private var diaryResponse: DiaryResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        diaryResponse = arguments?.getParcelable("diaryResponse")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ObserveErrors.observe(viewLifecycleOwner, requireContext(),
            viewModel.emptyDiaryHeaderErrorEntity, viewModel.emptyDiaryCommentErrorEntity, viewModel.insertDiaryErrorEntity, viewModel.emptyDiaryIdErrorEntity, viewModel.deleteDiaryErrorEntity, viewModel.updateDiaryErrorEntity)

        onClick()

        observeReadyToGoDiaryFragment()

    }

    private fun onClick(){
        onClickForNewDiary()
        onClickForOldDiary()
    }

    private fun onClickForNewDiary() {

        if(diaryResponse==null){

            binding.deleteImageView.visibility = View.GONE

            dateClicked()
            saveClicked()

        }

    }

    private fun onClickForOldDiary() {

        if(diaryResponse!=null){

            binding.calendarView.date = diaryResponse!!.date!!.time

            calendar.time = diaryResponse!!.date!!

            binding.headerText.setText(diaryResponse!!.header)
            binding.commentText.setText(diaryResponse!!.comment)

            dateClicked()
            updateClicked()
            deleteClicked()

        }

    }

    private fun dateClicked(){
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
    }

    private fun saveClicked(){
        binding.saveImageView.setOnClickListener {
            binding.apply {
                viewModel.insertDiary(DiaryRequest(header = headerText.text.toString(), comment = commentText.text.toString(), date = calendar.time))
            }
        }
    }

    private fun updateClicked(){
        binding.saveImageView.setOnClickListener {
            binding.apply {
                viewModel.updateDiary(DiaryRequest(id = diaryResponse!!.id, header = headerText.text.toString(), comment = commentText.text.toString(), date = calendar.time))
            }
        }
    }

    private fun deleteClicked(){
        binding.deleteImageView.setOnClickListener {
            binding.apply {
                viewModel.deleteDiary(DiaryRequest(id = diaryResponse!!.id, header = diaryResponse!!.header!!, comment = diaryResponse!!.comment!!, date = diaryResponse!!.date!!))
            }
        }
    }

    private fun observeReadyToGoDiaryFragment(){
        viewModel.readyToGoDiaryFragmentB.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it==true) toDiaryFragment()
        })
    }

    private fun toDiaryFragment(){
        Navigation.findNavController(requireView()).navigate(DiaryDetailsFragmentDirections.actionDiaryDetailsFragmentToDiaryFragment())
    }

}