package com.jetpack.currencyconverter.common

import kotlin.coroutines.CoroutineContext

interface DispatcherProvider {
    fun UI(): CoroutineContext
    fun IO(): CoroutineContext
}