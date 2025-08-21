package com.aariz.sportsapp.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<ChatMessage>()

    fun submit(message: ChatMessage) {
        items.add(message)
        notifyItemInserted(items.size - 1)
    }

    fun showBotLoading(prefix: String? = "⚡"): Long {
        val id = System.currentTimeMillis()
        items.add(ChatMessage(id = id, role = ChatMessage.Role.BOT, text = "", isLoading = true, prefix = prefix))
        notifyItemInserted(items.size - 1)
        return id
    }

    fun replaceLoading(id: Long, text: String, prefix: String? = "⚡") {
        val idx = items.indexOfFirst { it.id == id }
        if (idx >= 0) {
            items[idx] = ChatMessage(id = id, role = ChatMessage.Role.BOT, text = text, isLoading = false, prefix = prefix)
            notifyItemChanged(idx)
        } else {
            submit(ChatMessage(role = ChatMessage.Role.BOT, text = text, prefix = prefix))
        }
    }

    override fun getItemViewType(position: Int): Int = when (items[position].role) {
        ChatMessage.Role.USER -> 1
        ChatMessage.Role.BOT -> 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == 1) {
            val v = inflater.inflate(R.layout.item_chat_user, parent, false)
            UserVH(v)
        } else {
            val v = inflater.inflate(R.layout.item_chat_bot, parent, false)
            BotVH(v)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = items[position]
        when (holder) {
            is UserVH -> holder.bind(msg)
            is BotVH -> holder.bind(msg)
        }
    }

    class UserVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tv: TextView = itemView.findViewById(R.id.tv_message)
        fun bind(m: ChatMessage) { tv.text = m.text }
    }

    class BotVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPrefix: TextView = itemView.findViewById(R.id.tv_prefix)
        private val tvMessage: TextView = itemView.findViewById(R.id.tv_message)
        private val loading: LinearLayout = itemView.findViewById(R.id.loading_container)
        fun bind(m: ChatMessage) {
            tvPrefix.text = m.prefix ?: ""
            tvPrefix.visibility = if ((m.prefix ?: "").isNotBlank()) View.VISIBLE else View.GONE
            if (m.isLoading) {
                tvMessage.visibility = View.GONE
                loading.visibility = View.VISIBLE
            } else {
                loading.visibility = View.GONE
                tvMessage.visibility = View.VISIBLE
                tvMessage.text = m.text
            }
        }
    }
}
