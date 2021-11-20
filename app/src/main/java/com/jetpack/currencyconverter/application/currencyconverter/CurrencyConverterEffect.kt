package com.jetpack.currencyconverter.application.currencyconverter

sealed class CurrencyConverterEffect {
    data class ShowToast(val message: String): CurrencyConverterEffect()
}
