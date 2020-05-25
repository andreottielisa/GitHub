package com.api.github.domain

import io.reactivex.Scheduler

internal interface SchedulerProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
}