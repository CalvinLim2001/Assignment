package com.example.assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.data.Tips

class TipAdapter : RecyclerView.Adapter<TipAdapter.ViewHolder>() {
    private var data: List<Tips> = emptyList()
    private var expandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tips, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

        val isExpanded = position == expandedPosition
        holder.expandButton.visibility = if (isExpanded) View.GONE else View.VISIBLE
        holder.collapseButton.visibility = if (isExpanded) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(tipsList: List<Tips>) {
        data = tipsList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tvTipTitle)
        private val firstSentenceTextView: TextView = itemView.findViewById(R.id.tvFirstSentence)
        private val tipContentTextView: TextView = itemView.findViewById(R.id.tvTipContent)
        val expandButton: ImageView = itemView.findViewById(R.id.expandButton)
        val collapseButton: ImageView = itemView.findViewById(R.id.collapseButton)
        val expandableLayout: LinearLayout = itemView.findViewById(R.id.expandableLayout)
        val expandCollapseLayout : RelativeLayout = itemView.findViewById(R.id.expandCollapseLayout)

        init {
            expandCollapseLayout.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    expandedPosition = if (position == expandedPosition) -1 else position
                    notifyDataSetChanged()
                }
            }

            collapseButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    expandedPosition = -1
                    notifyItemChanged(position)
                }
            }
        }

        fun bind(tips: Tips) {
            titleTextView.text = tips.title
            tipContentTextView.text = tips.content
            // Display the first 30 words followed by ellipsis
            val first30Words = tips.content.split(" ").take(30).joinToString(" ")
            val truncatedContent = if (tips.content.length > first30Words.length) "$first30Words..." else first30Words
            firstSentenceTextView.text = truncatedContent

            val isExpanded = adapterPosition == expandedPosition
            expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
            firstSentenceTextView.visibility = if (isExpanded) View.GONE else View.VISIBLE

            // Set visibility of expandButton and collapseButton based on expansion state
            expandButton.visibility = if (isExpanded) View.GONE else View.VISIBLE
            collapseButton.visibility = if (isExpanded) View.VISIBLE else View.GONE
        }
    }
}