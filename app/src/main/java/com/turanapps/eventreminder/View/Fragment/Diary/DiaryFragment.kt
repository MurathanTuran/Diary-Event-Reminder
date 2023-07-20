package com.turanapps.eventreminder.View.Fragment.Diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.turanapps.eventreminder.Adapter.DiaryRecyclerAdapter
import com.turanapps.eventreminder.Error.ObserveErrors
import com.turanapps.eventreminder.ViewModel.Fragment.Diary.DiaryViewModel
import com.turanapps.eventreminder.databinding.FragmentDiaryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DiaryFragment: Fragment() {

    private lateinit var binding: FragmentDiaryBinding

    private lateinit var diaryAdapter: DiaryRecyclerAdapter

    private val viewModel: DiaryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ObserveErrors.observe(viewLifecycleOwner, requireContext(),
            viewModel.getDiariesErrorEntity)

        onClick()

        diaryAdapter = DiaryRecyclerAdapter(viewModel.getAllDiaries())
        binding.diaryRecyclerView.adapter = diaryAdapter
        binding.diaryRecyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)

    }

    private fun onClick(){
        binding.addDiaryFAB.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(DiaryFragmentDirections.actionDiaryFragmentToDiaryDetailsFragment(null))
        }
    }

}