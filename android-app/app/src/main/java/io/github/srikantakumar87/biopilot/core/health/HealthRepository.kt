package io.github.srikantakumar87.biopilot.core.health

import android.health.connect.TimeRangeFilter
import android.health.connect.datatypes.StepsRecord
import androidx.health.connect.client.request.AggregateRequest
import io.github.srikantakumar87.biopilot.core.model.DailyHeartRate
import io.github.srikantakumar87.biopilot.core.model.DailySleep
import io.github.srikantakumar87.biopilot.core.model.DailySteps
import io.github.srikantakumar87.biopilot.core.model.HeartRateSummary

interface HealthRepository {

    suspend fun getTodaySteps(): Long

    suspend fun getLatestHeartRate(): Long?

    suspend fun getTodaySleepHours(): Double

    suspend fun getLatestWeight(): Double?

    suspend fun getWeeklySteps(): List<DailySteps>

    suspend fun getWeeklySleep(): List<DailySleep>

    suspend fun getAverageSleepHours(): Double

    suspend fun getWeeklyHeartRates(): List<DailyHeartRate>

    suspend fun getHeartRateSummary(): HeartRateSummary
}