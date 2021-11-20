package com.jetpack.currencyconverter.domain.repository

import com.jetpack.currencyconverter.common.ResultWrapper
import com.jetpack.currencyconverter.domain.entity.ConversionFactor
import com.jetpack.currencyconverter.domain.entity.Currency

interface ICurrencyRepository {
    suspend fun getAllCurrencies(): ResultWrapper<Exception, List<Currency>>

    suspend fun getConversionFactor(
        baseCurrency: Currency,
        targetCurrency: Currency
    ): ResultWrapper<Exception, ConversionFactor>
}