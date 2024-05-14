package com.example.assignment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assignment.databinding.FragmentBudgetBinding
import com.example.assignment.databinding.FragmentMainMenuBinding


class BudgetFragment : Fragment() {
    private lateinit var binding: FragmentBudgetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val calculateIntent = Intent(requireContext(), CalculateActivity::class.java)
        binding = FragmentBudgetBinding.inflate(inflater, container, false)
        binding.btnCalculate.setOnClickListener(){
            startActivity(calculateIntent)
        }
// Inflate the layout for this fragment
        return binding.root
    }
}