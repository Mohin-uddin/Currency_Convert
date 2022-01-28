package com.example.currencyconvert.data.model

data class CurrencyResponse(
    val base: String,
    val ms: Int,
    val results: Results,
    val updated: String
)