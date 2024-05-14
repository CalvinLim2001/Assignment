package com.example.assignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.GridLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.assignment.adapter.ItemAdapter
import com.example.assignment.data.Item
import com.example.assignment.databinding.ActivityCalculateBinding
import com.example.assignment.databinding.ActivityProfileBinding
import com.example.assignment.databinding.FragmentBudgetBinding

class CalculateActivity : AppCompatActivity() {
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var itemList: MutableList<Item>
    private lateinit var binding: ActivityCalculateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val months = resources.getStringArray(R.array.Months)
        val spinner = binding.monthSpinner
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                R.layout.spinner_item, months
            )
            spinner.adapter = adapter
        }

        binding.MonthlyLayout.setOnClickListener {
            val monthlyCollapsible = binding.monthlyCollapsible
            val monthIcon = binding.MonthIcon
            if (monthlyCollapsible.visibility == View.VISIBLE) {
                monthlyCollapsible.visibility = View.GONE
                monthIcon.setImageResource(R.drawable.open)
            } else {
                monthlyCollapsible.visibility = View.VISIBLE
                monthIcon.setImageResource(R.drawable.close)
            }
        }

        binding.DailyLayout.setOnClickListener {
            val dailyCollapsible = binding.dailyCollapsible
            val dayIcon = binding.DayIcon
            if (dailyCollapsible.visibility == View.VISIBLE) {
                dailyCollapsible.visibility = View.GONE
                dayIcon.setImageResource(R.drawable.open)
            } else {
                dailyCollapsible.visibility = View.VISIBLE
                dayIcon.setImageResource(R.drawable.close)
            }
        }

        // Initialize the RecyclerView
        itemList = mutableListOf()
        itemAdapter = ItemAdapter(itemList)
        binding.dailyRecycler.layoutManager = GridLayoutManager(this, 3)
        binding.dailyRecycler.adapter = itemAdapter

        binding.dayAdd.setOnClickListener {
            showPopup()
        }
    }
    private fun showPopup() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.popup_calculate, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add Item")
            .setPositiveButton("Add") { dialog, which ->
                val itemName = dialogView.findViewById<EditText>(R.id.editTextItem).text.toString()
                val day = dialogView.findViewById<EditText>(R.id.editTextDay).text.toString().toInt()
                val price = dialogView.findViewById<EditText>(R.id.editTextPrice).text.toString().toInt()
                val newItem = Item(itemName, day, price)
                itemList.add(newItem)
                itemAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
        builder.show()
    }
}
