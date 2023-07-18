package com.turanapps.eventreminder.View.Fragment.Calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.turanapps.eventreminder.Adapter.DiaryRecyclerAdapter
import com.turanapps.eventreminder.Adapter.EventRecyclerAdapter
import com.turanapps.eventreminder.DTO.Response.DiaryResponse
import com.turanapps.eventreminder.DTO.Response.EventResponse
import com.turanapps.eventreminder.ViewModel.Fragment.Calendar.CalendarViewModel
import com.turanapps.eventreminder.databinding.FragmentCalendarBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*
import kotlin.collections.ArrayList

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding

    private lateinit var eventAdapter: EventRecyclerAdapter

    private lateinit var diaryAdapter: DiaryRecyclerAdapter

    private val calendar = Calendar.getInstance()

    private val viewModel: CalendarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dateClicked()

        dropDownClicked()

        eventAdapter = EventRecyclerAdapter(viewModel.getEventsByDate(calendar.time))
        binding.eventsRecyclerView.adapter = eventAdapter
        binding.eventsRecyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)

        diaryAdapter = DiaryRecyclerAdapter(viewModel.getDiariesByDate(calendar.time))
        binding.diariesRecyclerView.adapter = diaryAdapter
        binding.diariesRecyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)

    }

    private fun dateClicked(){
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->

            binding.thereAreNoDiariesText.visibility = View.GONE
            binding.diariesRecyclerView.visibility = View.GONE

            binding.thereAreNoEventsText.visibility = View.GONE
            binding.eventsRecyclerView.visibility = View.GONE

            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val selectedDate = calendar.time

            eventAdapter.updateEvents(viewModel.getEventsByDate(selectedDate))
            diaryAdapter.updateDiaries(viewModel.getDiariesByDate(selectedDate))

            println(calendar.time)
            println(viewModel.getEventsByDate(selectedDate))
            println(viewModel.getDiariesByDate(selectedDate))

        }
    }

    private fun dropDownClicked(){
        eventsDropDownClicked()
        diariesDropDownClicked()
    }

    private fun eventsDropDownClicked(){
        binding.eventsDropDown.setOnClickListener {
            if(eventAdapter.itemCount == 0){
                println("events 0")
                if(binding.thereAreNoEventsText.visibility == View.GONE){
                    binding.thereAreNoEventsText.visibility = View.VISIBLE
                    println("events 0 made visible")
                }
                else if(binding.thereAreNoEventsText.visibility == View.VISIBLE){
                    binding.thereAreNoEventsText.visibility = View.GONE
                    println("events 0 made gone")
                }
            }
            else{
                if(binding.eventsRecyclerView.visibility == View.GONE){
                    binding.eventsRecyclerView.visibility = View.VISIBLE
                }
                else if(binding.eventsRecyclerView.visibility == View.VISIBLE){
                    binding.eventsRecyclerView.visibility = View.GONE
                }
            }
        }
    }

    private fun diariesDropDownClicked(){
        binding.diariesDropDown.setOnClickListener {
            if(diaryAdapter.itemCount == 0){
                if(binding.thereAreNoDiariesText.visibility == View.GONE){
                    binding.thereAreNoDiariesText.visibility = View.VISIBLE
                }
                else if(binding.thereAreNoDiariesText.visibility == View.VISIBLE){
                    binding.thereAreNoDiariesText.visibility = View.GONE
                }
            }
            else{
                if(binding.diariesRecyclerView.visibility == View.GONE){
                    binding.diariesRecyclerView.visibility = View.VISIBLE
                }
                else if(binding.diariesRecyclerView.visibility == View.VISIBLE){
                    binding.diariesRecyclerView.visibility = View.GONE
                }
            }
        }
    }

}