package com.aariz.sportsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.models.Commentator

class CommentatorAdapter(
    private val commentatorList: List<Commentator>,
    private val onItemClick: (Commentator) -> Unit
) : RecyclerView.Adapter<CommentatorAdapter.CommentatorViewHolder>() {

    inner class CommentatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentatorImage: ImageView = itemView.findViewById(R.id.commentatorImage)
        val commentatorName: TextView = itemView.findViewById(R.id.commentatorName)
        val commentatorCountry: TextView = itemView.findViewById(R.id.commentatorCountry)

        fun bind(commentator: Commentator) {
            commentatorName.text = commentator.name
            commentatorCountry.text = commentator.country
            commentatorImage.setImageResource(commentator.imageResId)

            // Handle click
            itemView.setOnClickListener {
                onItemClick(commentator)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentatorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.commentator_item, parent, false)
        return CommentatorViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentatorViewHolder, position: Int) {
        holder.bind(commentatorList[position])
    }

    override fun getItemCount(): Int = commentatorList.size
}
