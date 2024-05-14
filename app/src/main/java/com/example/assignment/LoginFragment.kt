package com.example.assignment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.assignment.data.Account
import com.example.assignment.databinding.FragmentLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.tvRegisterAccount.setOnClickListener() {
            val fragmentRegister = RegisterFragment()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.ContainerProfile, fragmentRegister).addToBackStack(null).commit()
            }
        }

        fun saveAccountDetails(context: Context, account: Account) {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences("AccountDetails", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("accountID", account.accountID)
            editor.putString("username", account.username)
            editor.putString("email", account.email)
            editor.putString("password", account.password)
            editor.putInt("loanAmount", account.loanAmount)
            editor.putInt("interestRate", account.interestRate)
            editor.putInt("budgetWarning", account.budgetWarning)
            editor.apply()
        }

        // Function to start MainActivity
        fun startMainActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }

        binding.loginButton.setOnClickListener() {
            val username: String = binding.tfLoginUsername.text.toString().trim()
            val password: String = binding.tfLoginPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Username and password are required", Toast.LENGTH_LONG).show()
            } else {
                val dbLogin = FirebaseDatabase.getInstance().getReference("Account")

                dbLogin.orderByChild("username").equalTo(username)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (snapshot in dataSnapshot.children) {
                                    val account = snapshot.getValue(Account::class.java)
                                    if (account != null && account.password == password) {
                                        // Login successful
                                        Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_LONG).show()
                                        saveAccountDetails(requireContext(), account)
                                        startMainActivity(requireContext())
                                        return
                                    } else {
                                        // Password doesn't match
                                        Toast.makeText(requireContext(), "Incorrect password", Toast.LENGTH_LONG).show()
                                        // Handle incorrect password, e.g., display error message
                                    }
                                }
                            } else {
                                // Account not found
                                Toast.makeText(requireContext(), "Account not found", Toast.LENGTH_LONG).show()
                                // Handle account not found, e.g., display error message
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            // Handle database error
                            Toast.makeText(requireContext(), "Database error", Toast.LENGTH_LONG).show()
                        }
                    })
            }
        }
        return binding.root
    }
}