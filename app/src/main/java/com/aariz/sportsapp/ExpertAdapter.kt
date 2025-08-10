package com.aariz.sportsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.models.Expert

class ExpertAdapter(
    private val expertList: List<Expert>,
    private val onItemClick: (Expert) -> Unit
) : RecyclerView.Adapter<ExpertAdapter.ExpertViewHolder>() {

    inner class ExpertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expertImage: ImageView = itemView.findViewById(R.id.expertImage)
        val expertName: TextView = itemView.findViewById(R.id.expertName)
        val expertSpecialization: TextView = itemView.findViewById(R.id.expertSpecialization)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpertViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.expert_item, parent, false)
        return ExpertViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpertViewHolder, position: Int) {
        val expert = expertList[position]
        holder.expertImage.setImageResource(expert.imageResId)
        holder.expertName.text = expert.name
        holder.expertSpecialization.text = expert.specialization

        holder.itemView.setOnClickListener { onItemClick(expert) }
    }

    override fun getItemCount() = expertList.size
}
