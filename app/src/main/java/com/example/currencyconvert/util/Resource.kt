package com.example.currencyconvert.util

import com.example.currencyconvert.data.model.CurrencyResponse

sealed class Resource<T> (val data: T?,message : String?){
    class Success<T>(data: T?) : Resource<T>(data,null)
    class Error<T> (message: String) : Resource<T>(null,message)
}
