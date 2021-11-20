package com.jetpack.currencyconverter.application

import kotlinx.coroutines.Job

abstract class BaseLogic<EVENT> {
    protected lateinit var jobTracker: Job

    abstract fun onEvent(event: EVENT)
}