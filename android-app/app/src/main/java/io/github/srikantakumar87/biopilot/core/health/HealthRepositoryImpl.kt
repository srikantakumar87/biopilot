package io.github.srikantakumar87.biopilot.core.health

import android.util.Log
import androidx.health.connect.client.records.HeartRateRecord
import javax.inject.Inject
import javax.inject.Singleton
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.records.SleepSessionRecord
import io.github.srikantakumar87.biopilot.core.model.DailySteps
import java.time.Duration
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@Singleton
class HealthRepositoryImpl @Inject constructor(
    private val healthConnectManager: HealthConnectManager
) : HealthRepository {

    override suspend fun getTodaySteps(): Long {

        val startOfDay = ZonedDateTime
            .now()
            .toLocalDate()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()

        val end = Instant.now()

        val response = healthConnectManager.client.aggregate(
            AggregateRequest(
                metrics = setOf(
                    StepsRecord.COUNT_TOTAL
                ),
                timeRangeFilter = TimeRangeFilter.between(
                    startOfDay,
                    end
                )
            )
        )

        return response[StepsRecord.COUNT_TOTAL] ?: 0L
    }

    override suspend fun getLatestHeartRate(): Long? {

        val response = healthConnectManager.client.readRecords(
            ReadRecordsRequest(
                recordType = HeartRateRecord::class,
                timeRangeFilter = TimeRangeFilter.before(Instant.now()),
                ascendingOrder = false,
                pageSize = 1
            )
        )

        val latestRecord = response.records.firstOrNull()

        return latestRecord
            ?.samples
            ?.lastOrNull()
            ?.beatsPerMinute
            ?.toLong()
    }

    override suspend fun getTodaySleepHours(): Double {

        val now = Instant.now()

        val startOfToday = ZonedDateTime.now()
            .toLocalDate()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()

        val response = healthConnectManager.client.readRecords(
            ReadRecordsRequest(
                recordType = SleepSessionRecord::class,
                timeRangeFilter = TimeRangeFilter.between(
                    now.minus(Duration.ofHours(36)),
                    now
                )
            )
        )

        val totalMinutes = response.records.sumOf { record ->

            val start = maxOf(record.startTime, startOfToday)
            val end = minOf(record.endTime, now)

            if (end.isAfter(start))
                Duration.between(start, end).toMinutes()
            else
                0L
        }

        return totalMinutes / 60.0
    }

    override suspend fun getLatestWeight(): Double? {
        val response = healthConnectManager.client.readRecords(
            ReadRecordsRequest(
                recordType = WeightRecord::class,
                timeRangeFilter = TimeRangeFilter.before(Instant.now()),
                ascendingOrder = false,
                pageSize = 1
            )
        )

        return response.records
            .firstOrNull()
            ?.weight
            ?.inKilograms
    }

    override suspend fun getWeeklySteps(): List<DailySteps> {

        val zoneId = ZoneId.systemDefault()
        val today = LocalDate.now()

        val weeklySteps = mutableListOf<DailySteps>()

        for (i in 6 downTo 0) {

            val date = today.minusDays(i.toLong())

            val start = date
                .atStartOfDay(zoneId)
                .toInstant()

            val end = date
                .plusDays(1)
                .atStartOfDay(zoneId)
                .toInstant()

            val response = healthConnectManager.client.aggregate(
                AggregateRequest(
                    metrics = setOf(StepsRecord.COUNT_TOTAL),
                    timeRangeFilter = TimeRangeFilter.between(start, end)
                )
            )

            val steps = response[StepsRecord.COUNT_TOTAL] ?: 0L

            weeklySteps.add(
                DailySteps(
                    date = date,
                    dayLabel = date.dayOfWeek.getDisplayName(
                        TextStyle.SHORT,
                        Locale.getDefault()
                    ),
                    steps = steps
                )
            )
        }

        return weeklySteps
    }
}