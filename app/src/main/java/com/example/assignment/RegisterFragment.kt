package com.example.assignment
import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.assignment.data.Account
import com.example.assignment.databinding.FragmentRegisterBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID
import java.util.regex.Matcher
import java.util.regex.Pattern


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var dbRefAccount : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        dbRefAccount = FirebaseDatabase.getInstance().getReference("Account")

        val btnRegister : Button =  binding.btnRegister

        val tfInterest = binding.tfInterest
        val tfBudget = binding.tfRegisterBudgetWarn
        val tfLoan = binding.tfRegisterLoan
        val numberRangeFilter = NumberRangeFilter(requireContext())
        tfInterest.filters = arrayOf(numberRangeFilter)
        tfBudget.filters = arrayOf(numberRangeFilter)
        tfLoan.filters = arrayOf(DecimalDigitsInputFilter(100,2))


        btnRegister.setOnClickListener() {
            val accountID = UUID.randomUUID().toString()
            val username: EditText = binding.tfRegisterUser
            val email: EditText = binding.tfRegisterEmail
            val password: EditText = binding.tfRegisterPassword
            val loanAmount: EditText = binding.tfRegisterLoan
            val interestRate: EditText = binding.tfInterest
            val budgetWarn: EditText = binding.tfRegisterBudgetWarn
            val confirmPassword : EditText = binding.tfConfirmPassword

            // Trim input strings
            val textUsername = username.text.toString().trim()
            val textEmail = email.text.toString().trim()
            val textPassword = password.text.toString().trim()
            val textConfirmPassword = confirmPassword.text.toString().trim()

            if (textUsername.isEmpty() || textEmail.isEmpty() || textPassword.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Username, email, and password are required",
                    Toast.LENGTH_LONG
                ).show()
            } else if (textPassword != textConfirmPassword) {
                Toast.makeText(
                    requireContext(),
                    "Passwords do not match",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val account = Account(
                    accountID,
                    textUsername,
                    textEmail,
                    textPassword,
                    Integer.parseInt(loanAmount.text.toString()),
                    Integer.parseInt(interestRate.text.toString()),
                    Integer.parseInt(budgetWarn.text.toString())
                )

                dbRefAccount.child(account.username).setValue(account).addOnCompleteListener {
                    Toast.makeText(requireContext(), "Data saved", Toast.LENGTH_LONG).show()
                }
                    .addOnFailureListener {
                        Toast.makeText(
                            requireContext(),
                            "Error ${it.toString()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }
        return binding.getRoot() // instead of another inflate return the binding view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    class NumberRangeFilter(private val context: Context) : InputFilter {
        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            val input = source.toString()
            val value = try {
                Integer.parseInt(input)
            } catch (e: NumberFormatException) {
                return ""
            }

            // Check if the input is within the range of 0 to 100
            return if (value in 0..100) {
                // Check if the resulting text will be within the range of 0 to 100 after replacement
                val newValue = dest.toString().substring(0, dstart) + input + dest.toString().substring(dend)
                val newValueInt = try {
                    Integer.parseInt(newValue)
                } catch (e: NumberFormatException) {
                    return ""
                }
                if (newValueInt in 0..100) {
                    null // Accept input
                } else {
                    Toast.makeText(context, "Input a number between 0 to 100", Toast.LENGTH_SHORT).show()
                    "" // Reject input
                }
            } else {
                "" // Reject input if it's outside the range
            }
        }
    }

    class DecimalDigitsInputFilter(digitsBeforeDecimal: Int, digitsAfterDecimal: Int) : InputFilter {

        var mPattern: Pattern = Pattern.compile("[0-9]{0,$digitsBeforeDecimal}+((\\.[0-9]{0,$digitsAfterDecimal})?)||(\\.)?")

        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            val matcher: Matcher = mPattern.matcher(
                dest?.subSequence(0, dstart).toString() + source?.subSequence(
                    start,
                    end
                ).toString() + dest?.subSequence(dend, dest.length).toString()
            )
            if (!matcher.matches())
                return ""
            else
                return null
        }
    }

}