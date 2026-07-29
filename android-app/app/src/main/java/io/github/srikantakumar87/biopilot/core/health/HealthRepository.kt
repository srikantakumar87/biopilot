package io.github.srikantakumar87.biopilot.core.health

import android.health.connect.TimeRangeFilter
import android.health.connect.datatypes.StepsRecord
import androidx.health.connect.client.request.AggregateRequest
import io.github.srikantakumar87.biopilot.core.model.DailySteps

interface HealthRepository {

    suspend fun getTodaySteps(): Long

    suspend fun getLatestHeartRate(): Long?

    suspend fun getTodaySleepHours(): Double

    suspend fun getLatestWeight(): Double?

    suspend fun getWeeklySteps(): List<DailySteps>
}