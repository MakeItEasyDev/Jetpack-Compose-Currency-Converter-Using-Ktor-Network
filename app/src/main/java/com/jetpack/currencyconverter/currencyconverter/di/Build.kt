package com.jetpack.currencyconverter.currencyconverter.di

import com.jetpack.currencyconverter.application.currencyconverter.CurrencyConverterViewModelImpl
import com.jetpack.currencyconverter.common.ProductionDispatcherProvider
import com.jetpack.currencyconverter.infrastructure.datasource.remote.RemoteDataSourceImpl
import com.jetpack.currencyconverter.infrastructure.repository.CurrencyRepositoryImpl

val viewModel = CurrencyConverterViewModelImpl(
    CurrencyRepositoryImpl(
        RemoteDataSourceImpl(
            baseUrl = "https://v6.exchangerate-api.com/v6/f26039575e84dab9918243b2",
            dispatcherProvider = ProductionDispatcherProvider
        )
    ),
    ProductionDispatcherProvider
)