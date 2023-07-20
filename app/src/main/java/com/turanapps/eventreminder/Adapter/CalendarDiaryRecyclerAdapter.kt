package com.turanapps.eventreminder.Adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.turanapps.eventreminder.DTO.Response.DiaryResponse
import com.turanapps.eventreminder.R
import com.turanapps.eventreminder.databinding.DiaryRecyclerRowBinding
import java.text.SimpleDateFormat
import javax.inject.Inject

class CalendarDiaryRecyclerAdapter @Inject constructor(
    private var diaryList: ArrayList<DiaryResponse>
): RecyclerView.Adapter<CalendarDiaryRecyclerAdapter.CalendarDiaryViewHolder>() {

    private val colorList = listOf(
        R.color.venetian_red,
        R.color.paolo_veronese_green,
        R.color.brown_chocolate,
        R.color.pastel_red,
        R.color.zomp,
        R.color.eggplant
    )

    class CalendarDiaryViewHolder(val binding: DiaryRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarDiaryViewHolder {
        val binding = DiaryRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarDiaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarDiaryViewHolder, position: Int) {
        val color = ContextCompat.getColor(holder.itemView.context, colorList[position%6])

        holder.itemView.backgroundTintList = ColorStateList.valueOf(color)

        val formatter = SimpleDateFormat("dd/MM/yyy")

        holder.binding.apply {
            diaryList[position].apply {
                headerTextRecyclerRow.text = header
                commentTextRecyclerRow.text = comment
                dateTextRecyclerRow.text = formatter.format(date)
            }
        }
    }

    override fun getItemCount(): Int {
        return diaryList.size
    }

    fun updateDiaries(newDiaries: ArrayList<DiaryResponse>) {
        diaryList = newDiaries
        notifyDataSetChanged()
    }

}
