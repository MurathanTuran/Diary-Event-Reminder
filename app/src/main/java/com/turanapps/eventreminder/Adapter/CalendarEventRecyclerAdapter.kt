package com.turanapps.eventreminder.Adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.turanapps.eventreminder.DTO.Response.EventResponse
import com.turanapps.eventreminder.R
import com.turanapps.eventreminder.databinding.EventRecyclerRowBinding
import java.text.SimpleDateFormat
import javax.inject.Inject

class CalendarEventRecyclerAdapter @Inject constructor(
    private var eventList: ArrayList<EventResponse>
): RecyclerView.Adapter<CalendarEventRecyclerAdapter.CalendarEventViewHolder>() {

    private val colorList = listOf(
        R.color.venetian_red,
        R.color.paolo_veronese_green,
        R.color.brown_chocolate,
        R.color.pastel_red,
        R.color.zomp,
        R.color.eggplant
    )

    class CalendarEventViewHolder(val binding: EventRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarEventViewHolder {
        val binding = EventRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarEventViewHolder, position: Int) {
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
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    fun updateEvents(newEvents: ArrayList<EventResponse>) {
        eventList = newEvents
        notifyDataSetChanged()
    }

}
