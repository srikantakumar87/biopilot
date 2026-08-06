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
import io.github.srikantakumar87.biopilot.core.ai.model.HealthSnapshot
import io.github.srikantakumar87.biopilot.core.model.BodyComposition
import io.github.srikantakumar87.biopilot.core.model.DailyHeartRate
import io.github.srikantakumar87.biopilot.core.model.DailySteps
import java.time.Duration
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import io.github.srikantakumar87.biopilot.core.model.DailySleep
import io.github.srikantakumar87.biopilot.core.model.DailyWeight
import io.github.srikantakumar87.biopilot.core.model.HeartRateSummary


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

        Log.d(
            "SleepDebug",
            "Records = ${response.records.size}"
        )

        response.records.forEach {

            Log.d(
                "SleepDebug",
                "${it.startTime} -> ${it.endTime}"
            )
        }

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
    override suspend fun getWeeklySleep(): List<DailySleep> {

        val zoneId = ZoneId.systemDefault()
        val today = LocalDate.now()

        val weeklySleep = mutableListOf<DailySleep>()

        for (i in 6 downTo 0) {

            val date = today.minusDays(i.toLong())

            val dayStart = date
                .atStartOfDay(zoneId)
                .toInstant()

            val dayEnd = date
                .plusDays(1)
                .atStartOfDay(zoneId)
                .toInstant()

            val response = healthConnectManager.client.readRecords(
                ReadRecordsRequest(
                    recordType = SleepSessionRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(
                        dayStart.minus(Duration.ofHours(12)),
                        dayEnd
                    )
                )
            )

            val totalMinutes = response.records.sumOf { record ->

                val start = maxOf(record.startTime, dayStart)
                val end = minOf(record.endTime, dayEnd)

                if (end.isAfter(start))
                    Duration.between(start, end).toMinutes()
                else
                    0L
            }

            weeklySleep.add(
                DailySleep(
                    date = date,
                    dayLabel = date.dayOfWeek.getDisplayName(
                        TextStyle.SHORT,
                        Locale.getDefault()
                    ),
                    hours = totalMinutes / 60.0
                )
            )
        }

        return weeklySleep
    }
    override suspend fun getAverageSleepHours(): Double {

        val weeklySleep = getWeeklySleep()

        if (weeklySleep.isEmpty()) {
            return 0.0
        }

        return weeklySleep
            .map { it.hours }
            .average()
    }
    override suspend fun getWeeklyHeartRates(): List<DailyHeartRate> {
        val zoneId = ZoneId.systemDefault()
        val today = LocalDate.now()

        val weeklyHeartRates = mutableListOf<DailyHeartRate>()

        for (i in 6 downTo 0) {

            val date = today.minusDays(i.toLong())

            val start = date
                .atStartOfDay(zoneId)
                .toInstant()

            val end = date
                .plusDays(1)
                .atStartOfDay(zoneId)
                .toInstant()

            val response = healthConnectManager.client.readRecords(
                ReadRecordsRequest(
                    recordType = HeartRateRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(
                        start,
                        end
                    )
                )
            )

            val averageHeartRate =
                response.records
                    .flatMap { it.samples }
                    .map { it.beatsPerMinute.toLong() }
                    .average()
                    .takeIf { !it.isNaN() }
                    ?.toLong()
                    ?: 0L

            weeklyHeartRates.add(
                DailyHeartRate(
                    date = date,
                    dayLabel = date.dayOfWeek.getDisplayName(
                        TextStyle.SHORT,
                        Locale.getDefault()
                    ),
                    heartRate = averageHeartRate
                )
            )
        }

        return weeklyHeartRates
    }

    override suspend fun getHeartRateSummary(): HeartRateSummary {

        val latest = getLatestHeartRate()
        val weeklyHeartRates = getWeeklyHeartRates()

        val weeklyAverage =
            if (weeklyHeartRates.isEmpty()) {
                0.0
            } else {
                weeklyHeartRates
                    .map { it.heartRate }
                    .average()
            }

        // Placeholder until we calculate true resting heart rate
        val restingEstimate = latest?.toDouble() ?: weeklyAverage

        return HeartRateSummary(



            latest = latest,
            weeklyAverage = weeklyAverage,
            restingEstimate = restingEstimate
        )
    }


    override suspend fun getWeeklyWeights(): List<DailyWeight> {

        val zoneId = ZoneId.systemDefault()
        val today = LocalDate.now()

        val weeklyWeights = mutableListOf<DailyWeight>()

        for (i in 6 downTo 0) {

            val date = today.minusDays(i.toLong())

            val start = date
                .atStartOfDay(zoneId)
                .toInstant()

            val end = date
                .plusDays(1)
                .atStartOfDay(zoneId)
                .toInstant()

            val response = healthConnectManager.client.readRecords(
                ReadRecordsRequest(
                    recordType = WeightRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(
                        start,
                        end
                    ),
                    ascendingOrder = false,
                    pageSize = 1
                )
            )

            val weight = response.records
                .firstOrNull()
                ?.weight
                ?.inKilograms
                ?: 0.0

            weeklyWeights.add(
                DailyWeight(
                    date = date,
                    dayLabel = date.dayOfWeek.getDisplayName(
                        TextStyle.SHORT,
                        Locale.getDefault()
                    ),
                    weight = weight
                )
            )
        }

        return weeklyWeights
    }

    override suspend fun getBodyComposition(): BodyComposition {

        return BodyComposition(
            bodyFatPercent = null,
            leanBodyMassKg = null
        )
    }

    override suspend fun getHealthSnapshot(): HealthSnapshot {

        val steps = getTodaySteps()

        val sleep = getAverageSleepHours()

        val heartRate = getLatestHeartRate()

        val weight = getLatestWeight()

        val bmi = calculateBMI(weight)

        return HealthSnapshot(
            steps = steps,
            averageSleepHours = sleep,
            latestHeartRate = heartRate,
            weight = weight,
            bmi = bmi
        )
    }

    private fun calculateBMI(
        weight: Double?
    ): Double? {

        if (weight == null) return null

        val heightMeters = 1.80   // TODO: Replace with user's saved height

        return weight / (heightMeters * heightMeters)
    }
}