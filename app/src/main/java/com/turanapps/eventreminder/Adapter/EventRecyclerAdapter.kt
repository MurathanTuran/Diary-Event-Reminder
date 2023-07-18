package com.turanapps.eventreminder.Adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.turanapps.eventreminder.DTO.Response.EventResponse
import com.turanapps.eventreminder.R
import com.turanapps.eventreminder.View.Fragment.Diary.DiaryFragmentDirections
import com.turanapps.eventreminder.View.Fragment.Event.EventFragmentDirections
import com.turanapps.eventreminder.ViewModel.Fragment.Diary.DiaryDetailsViewModel
import com.turanapps.eventreminder.ViewModel.Fragment.Event.EventDetailsViewModel
import com.turanapps.eventreminder.databinding.EventRecyclerRowBinding
import java.text.SimpleDateFormat
import javax.inject.Inject

class EventRecyclerAdapter @Inject constructor(
    private var eventList: ArrayList<EventResponse>
    ): RecyclerView.Adapter<EventRecyclerAdapter.EventViewHolder>() {

    private val colorList = listOf(
        R.color.venetian_red,
        R.color.paolo_veronese_green,
        R.color.brown_chocolate,
        R.color.pastel_red,
        R.color.zomp,
        R.color.eggplant
    )

    class EventViewHolder(val binding: EventRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = EventRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {

        val color = ContextCompat.getColor(holder.itemView.context, colorList[position%6])

        holder.itemView.backgroundTintList = ColorStateList.valueOf(color)

        val formatter = SimpleDateFormat("dd/MM/yyy\nHH:mm")

        holder.binding.apply {
            eventList[position].apply {
                headerTextRecyclerRow.text = header
                commentTextRecyclerRow.text = comment
                dateTextRecyclerRow.text = formatter.format(date)
            }
        }

        holder.itemView.setOnClickListener {
            Navigation.findNavController(holder.binding.root).navigate(EventFragmentDirections.actionEventFragmentToEventDetailsFragment(eventList[position]))
        }

    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    fun updateEvents(newEvents: ArrayList<EventResponse>) {
        eventList = newEvents
        notifyDataSetChanged()
    }

}