package com.aariz.sportsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

// UI model for home scorecard items
data class HomeScorecardItem(
    val matchId: String?,
    val matchType: String?,
    val status: String?,
    val t1: String?,
    val t2: String?,
    val t1s: String?,
    val t2s: String?,
    val t1imgUrl: String?,
    val t2imgUrl: String?,
    val series: String?,
    val isPlaceholder: Boolean = false
)

class HomeScorecardAdapter(
    private var items: List<HomeScorecardItem>,
    private val onClick: ((HomeScorecardItem) -> Unit)? = null,
    private val onScheduleClick: (() -> Unit)? = null
) : RecyclerView.Adapter<HomeScorecardAdapter.ScorecardVH>() {

    inner class ScorecardVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHeader: TextView = itemView.findViewById(R.id.tv_header)
        val tvT1: TextView = itemView.findViewById(R.id.tv_t1)
        val tvT2: TextView = itemView.findViewById(R.id.tv_t2)
        val tvT1s: TextView = itemView.findViewById(R.id.tv_t1s)
        val tvT2s: TextView = itemView.findViewById(R.id.tv_t2s)
        val ivT1: ImageView = itemView.findViewById(R.id.iv_t1_img)
        val ivT2: ImageView = itemView.findViewById(R.id.iv_t2_img)
        val tvStatus: TextView = itemView.findViewById(R.id.tv_status)
        val tvSchedule: TextView = itemView.findViewById(R.id.tv_schedule)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScorecardVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_home_scorecard, parent, false)
        // Make every card equal width relative to the recycler width for horizontal pager-like UI
        val targetWidth = (parent.measuredWidth * 0.85f).toInt().coerceAtLeast(1)
        // Let the height be driven by item_home_scorecard.xml (wrap_content/minHeight)
        // BUT preserve the XML margins by copying them into the new LayoutParams
        val existing = v.layoutParams as ViewGroup.MarginLayoutParams
        val lp = RecyclerView.LayoutParams(targetWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.setMargins(existing.leftMargin, existing.topMargin, existing.rightMargin, existing.bottomMargin)
        v.layoutParams = lp
        return ScorecardVH(v)
    }

    override fun onBindViewHolder(holder: ScorecardVH, position: Int) {
        val item = items[position]
        val isPlaceholder = item.isPlaceholder

        val type = item.matchType?.uppercase() ?: ""
        val series = item.series ?: ""
        holder.tvHeader.text = listOf(type.takeIf { it.isNotEmpty() }, series.takeIf { it.isNotEmpty() })
            .filterNotNull()
            .joinToString(" • ")

        holder.tvT1.text = item.t1 ?: "—"
        holder.tvT2.text = item.t2 ?: "—"
        holder.tvT1s.text = item.t1s ?: if (isPlaceholder) "" else ""
        holder.tvT2s.text = item.t2s ?: if (isPlaceholder) "" else ""
        holder.tvStatus.text = if (isPlaceholder) "" else (item.status ?: "")

        // Load images from URL (API)
        val placeholder = R.drawable.ic_flag
        if (!item.t1imgUrl.isNullOrEmpty() && !isPlaceholder) {
            Glide.with(holder.itemView.context)
                .load(item.t1imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeholder)
                .error(placeholder)
                .into(holder.ivT1)
        } else {
            holder.ivT1.setImageResource(placeholder)
        }
        if (!item.t2imgUrl.isNullOrEmpty() && !isPlaceholder) {
            Glide.with(holder.itemView.context)
                .load(item.t2imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeholder)
                .error(placeholder)
                .into(holder.ivT2)
        } else {
            holder.ivT2.setImageResource(placeholder)
        }

        // Dim and disable click for placeholder items
        holder.itemView.alpha = if (isPlaceholder) 0.6f else 1f
        holder.itemView.isClickable = !isPlaceholder
        holder.itemView.setOnClickListener { if (!isPlaceholder) onClick?.invoke(item) }

        // Schedule click should take user to ScheduleFragment
        holder.tvSchedule.setOnClickListener { onScheduleClick?.invoke() }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<HomeScorecardItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
