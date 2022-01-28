package com.example.currencyconvert.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconvert.data.model.Results
import com.example.currencyconvert.util.DispatcherProvider
import com.example.currencyconvert.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    sealed class CurrencyEvent {
        class Success(val resultTest: String) : CurrencyEvent()
        class Failure(val error: String) : CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    fun convert(
        amountStr: String,
        fromStr: String,
        toStr: String
    ) {
        val amount = amountStr.toFloatOrNull()
        if (amount == null) {
            _conversion.value = CurrencyEvent.Failure("Not a valid Amount")
            return
        }

        viewModelScope.launch(dispatcher.io) {
            _conversion.value = CurrencyEvent.Loading
            when (val reteResponse = repository.getRates(fromStr, "91b2cb9c12-337de0a1c2-r6cuw7")) {
                is Resource.Error -> _conversion.value =
                    CurrencyEvent.Failure(reteResponse.data?.ms.toString())
                is Resource.Success -> {
                    val result = convertCurrency(toStr, reteResponse.data!!.results)
                    if (result == null) {
                        _conversion.value = CurrencyEvent.Failure("Unexpected value")
                    } else {
                        var convertCurrency = round(amount * result * 100) / 100

                        _conversion.value = CurrencyEvent.Success(
                            "$amount $fromStr = $convertCurrency $toStr"
                        )
                    }
                }
            }
        }
    }

    fun convertCurrency(toStr: String, rates: Results) = when (toStr) {
        "BDT" -> rates.bDT
        "CAD" -> rates.cAD
        "HKD" -> rates.hKD
        "ISK" -> rates.iSK
        "EUR" -> rates.eUR
        "PHP" -> rates.pHP
        "DKK" -> rates.dKK
        "HUF" -> rates.hUF
        "CZK" -> rates.cZK
        "AUD" -> rates.aUD
        "RON" -> rates.rON
        "SEK" -> rates.sEK
        "IDR" -> rates.iDR
        "INR" -> rates.iNR
        "BRL" -> rates.bRL
        "RUB" -> rates.rUB
        "HRK" -> rates.hRK
        "JPY" -> rates.jPY
        "THB" -> rates.tHB
        "CHF" -> rates.cHF
        "SGD" -> rates.sGD
        "PLN" -> rates.pLN
        "BGN" -> rates.bGN
        "CNY" -> rates.cNY
        "NOK" -> rates.nOK
        "NZD" -> rates.nZD
        "ZAR" -> rates.zAR
        "USD" -> rates.uSD
        "MXN" -> rates.mXN
        "ILS" -> rates.iLS
        "GBP" -> rates.gBP
        "KRW" -> rates.kRW
        "MYR" -> rates.mYR

        else -> null

    }


}
