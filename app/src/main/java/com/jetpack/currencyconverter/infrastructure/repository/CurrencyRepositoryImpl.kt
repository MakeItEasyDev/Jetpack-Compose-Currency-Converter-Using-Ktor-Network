package com.jetpack.currencyconverter.infrastructure.repository

import com.jetpack.currencyconverter.common.ResultWrapper
import com.jetpack.currencyconverter.domain.entity.ConversionFactor
import com.jetpack.currencyconverter.domain.entity.Currency
import com.jetpack.currencyconverter.domain.repository.ICurrencyRepository
import com.jetpack.currencyconverter.infrastructure.datasource.remote.IRemoteDataSource
import com.jetpack.currencyconverter.infrastructure.datasource.remote.dto.toDomain

class CurrencyRepositoryImpl(
    private val remoteDataSource: IRemoteDataSource
): ICurrencyRepository {
    override suspend fun getAllCurrencies(): ResultWrapper<Exception, List<Currency>> =
        when (val response = remoteDataSource.getAllCurrencies()) {
            is ResultWrapper.Failure -> response
            is ResultWrapper.Success -> ResultWrapper.build {
                response.result.toDomain()
            }
        }

    override suspend fun getConversionFactor(
        baseCurrency: Currency,
        targetCurrency: Currency
    ): ResultWrapper<Exception, ConversionFactor> =
        when (val response = remoteDataSource.getConversionFactor(baseCurrency, targetCurrency)) {
            is ResultWrapper.Failure -> response
            is ResultWrapper.Success -> ResultWrapper.build {
                response.result.toDomain(baseCurrency, targetCurrency)
            }
        }
}




















