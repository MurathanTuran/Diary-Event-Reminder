package com.turanapps.eventreminder.Adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.turanapps.eventreminder.DTO.Response.DiaryResponse
import com.turanapps.eventreminder.DTO.Response.EventResponse
import com.turanapps.eventreminder.R
import com.turanapps.eventreminder.View.Fragment.Diary.DiaryFragmentDirections
import com.turanapps.eventreminder.databinding.DiaryRecyclerRowBinding
import java.text.SimpleDateFormat
import javax.inject.Inject

class DiaryRecyclerAdapter @Inject constructor(
    private var diaryList: ArrayList<DiaryResponse>
    ): RecyclerView.Adapter<DiaryRecyclerAdapter.DiaryViewHolder>() {

    private val colorList = listOf(
        R.color.venetian_red,
        R.color.paolo_veronese_green,
        R.color.brown_chocolate,
        R.color.pastel_red,
        R.color.zomp,
        R.color.eggplant
    )

    class DiaryViewHolder(val binding: DiaryRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val binding = DiaryRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {

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

        holder.itemView.setOnClickListener {
            Navigation.findNavController(holder.binding.root).navigate(DiaryFragmentDirections.actionDiaryFragmentToDiaryDetailsFragment(diaryList[position]))
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
