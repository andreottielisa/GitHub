package com.api.github

import com.api.github.domain.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

internal class TestSchedulerProvider : SchedulerProvider {
    override fun ui(): Scheduler = Schedulers.single()
    override fun io(): Scheduler = Schedulers.single()
}