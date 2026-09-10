package com.hisab.rasels.data

data class Transaction(
    val id: Long = System.currentTimeMillis(),
    val amount: Double,
    val type: String, // "income" or "expense"
    val category: String,
    val date: String,
    val note: String = ""
)