package com.turanapps.eventreminder.View.Fragment.Event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.turanapps.eventreminder.Adapter.EventRecyclerAdapter
import com.turanapps.eventreminder.Error.ObserveErrors
import com.turanapps.eventreminder.ViewModel.Fragment.Event.EventViewModel
import com.turanapps.eventreminder.databinding.FragmentEventBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class EventFragment: Fragment() {

    private lateinit var binding: FragmentEventBinding

    private lateinit var eventAdapter: EventRecyclerAdapter

    private val viewModel: EventViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ObserveErrors.observe(viewLifecycleOwner, requireContext(),
            viewModel.getEventsErrorEntity,
            viewModel.deleteEventsErrorEntity,
        )

        viewModel.deletePassedEvents()

        refreshEvents()

        onClick()

        eventAdapter = EventRecyclerAdapter(viewModel.getAllEvents())
        binding.eventRecyclerView.adapter = eventAdapter
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)

    }

    private fun onClick(){
        binding.addEventFAB.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(EventFragmentDirections.actionEventFragmentToEventDetailsFragment(null))
        }
    }

    private fun refreshEvents(){
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.performRefresh()
            stateWhileRefreshing()
        }
    }

    private fun stateWhileRefreshing(){
        viewModel.refreshState.observe(viewLifecycleOwner, Observer { refreshing ->
            if(refreshing == true){
                binding.eventRecyclerView.visibility = View.GONE
            }
            else{
                eventAdapter.updateEvents(viewModel.getAllEvents())
                binding.eventRecyclerView.visibility = View.VISIBLE
                binding.swipeRefreshLayout.isRefreshing = false
            }
        })
    }

}