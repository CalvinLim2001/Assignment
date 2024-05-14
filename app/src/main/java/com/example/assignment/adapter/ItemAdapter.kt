package com.example.assignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.data.Item

// RecyclerView adapter
class ItemAdapter(private val itemList: MutableList<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    // ViewHolder class
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        private val dayTextView: TextView = itemView.findViewById(R.id.dayTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)

        fun bind(item: Item) {
            itemNameTextView.text = item.itemName
            dayTextView.text = item.day.toString()
            priceTextView.text = item.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.calculate_items, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = itemList.size
}