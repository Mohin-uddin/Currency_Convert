package com.example.currencyconvert.main

import com.example.currencyconvert.data.model.CurrencyResponse
import com.example.currencyconvert.util.Resource

interface MainRepository {

    suspend fun getRates(from:String,apiKey:String) : Resource<CurrencyResponse>
}