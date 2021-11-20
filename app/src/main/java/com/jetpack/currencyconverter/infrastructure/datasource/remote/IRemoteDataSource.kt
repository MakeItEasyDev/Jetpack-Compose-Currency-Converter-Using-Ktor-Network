package com.jetpack.currencyconverter.infrastructure.datasource.remote

import com.jetpack.currencyconverter.common.ResultWrapper
import com.jetpack.currencyconverter.domain.entity.Currency
import com.jetpack.currencyconverter.infrastructure.datasource.remote.dto.PairConversion
import com.jetpack.currencyconverter.infrastructure.datasource.remote.dto.SupportedCodes

interface IRemoteDataSource {
    suspend fun getAllCurrencies(): ResultWrapper<Exception, SupportedCodes>

    suspend fun getConversionFactor(
        baseCurrency: Currency,
        targetCurrency: Currency
    ): ResultWrapper<Exception, PairConversion>
}