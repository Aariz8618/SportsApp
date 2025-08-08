package com.aariz.sportsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.models.Commentator

class CommentatorAdapter(private val commentatorList: List<Commentator>) :
    RecyclerView.Adapter<CommentatorAdapter.CommentatorViewHolder>() {

    inner class CommentatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentatorImage: ImageView = itemView.findViewById(R.id.commentatorImage)
        val commentatorName: TextView = itemView.findViewById(R.id.commentatorName)
        val commentatorCountry: TextView = itemView.findViewById(R.id.commentatorCountry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentatorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.commentator_item, parent, false)
        return CommentatorViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentatorViewHolder, position: Int) {
        val commentator = commentatorList[position]
        holder.commentatorName.text = commentator.name
        holder.commentatorCountry.text = commentator.country
        holder.commentatorImage.setImageResource(commentator.imageResId)
    }

    override fun getItemCount(): Int = commentatorList.size
}