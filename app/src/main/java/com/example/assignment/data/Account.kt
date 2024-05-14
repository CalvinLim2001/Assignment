package com.example.assignment.data

data class Account(
    var accountID : String = "",
    var username : String = "",
    var email : String = "",
    var password : String = "",
    var loanAmount : Int = 0,
    var interestRate : Int = 0,
    var budgetWarning : Int = 0
)