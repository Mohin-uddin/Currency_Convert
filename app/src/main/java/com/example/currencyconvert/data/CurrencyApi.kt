package com.example.currencyconvert.data

import com.example.currencyconvert.data.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("fetch-all")
    suspend fun getCurrency(
        @Query("from") from:String,
        @Query("api_key") apiKey:String
    ): Response<CurrencyResponse>

}