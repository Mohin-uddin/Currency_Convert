package com.example.currencyconvert.main

import com.example.currencyconvert.data.CurrencyApi
import com.example.currencyconvert.data.model.CurrencyResponse
import com.example.currencyconvert.util.Resource
import java.lang.Exception
import javax.inject.Inject

class ImplementMainRepository @Inject constructor(
   private val currencyApi: CurrencyApi
): MainRepository{
    override suspend fun getRates(from: String, apiKey: String): Resource<CurrencyResponse> {
        return try {
            val response = currencyApi.getCurrency(from,apiKey)
            val result= response.body()
            if (response.isSuccessful && response != null)
            {
               Resource.Success(result)
            }else{
                Resource.Error(response.message())
            }
        } catch (error : Exception){
            return Resource.Error(error.message?:"An error Accourd")
        }
    }

}