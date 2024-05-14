package com.example.assignment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assignment.databinding.FragmentMainMenuBinding


class MainMenuFragment : Fragment() {
    private lateinit var binding: FragmentMainMenuBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        val intent = Intent(requireContext(), TipsActivity::class.java)
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        binding.budgetButton.setOnClickListener(){
            val fragmentBudget = BudgetFragment()
            parentFragmentManager.beginTransaction().apply{replace(R.id.MainContainer, fragmentBudget).addToBackStack(null).commit()}
        }
        binding.tipsButton.setOnClickListener(){
            startActivity(intent)
        }
        return binding.root
    }
}