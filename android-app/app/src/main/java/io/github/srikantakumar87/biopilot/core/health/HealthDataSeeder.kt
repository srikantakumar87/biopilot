package io.github.srikantakumar87.biopilot.core.health

import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.StepsRecord
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton
import androidx.health.connect.client.records.metadata.Metadata

import java.time.Duration
import java.time.Instant
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.HeartRateRecord.Sample


import java.time.LocalDate
import java.time.LocalTime
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.units.Mass
import androidx.health.connect.client.deleteRecords
import androidx.health.connect.client.time.TimeRangeFilter

@Singleton
class HealthDataSeeder @Inject constructor(
    private val healthConnectManager: HealthConnectManager
) {

    private val client: HealthConnectClient
        get() = healthConnectManager.client

    suspend fun insertTodaySteps(
        steps: Long = 8500
    ) {
        insertSteps(
            LocalDate.now(),
            steps
        )
    }
    private suspend fun insertSteps(
        date: LocalDate,
        steps: Long
    ) {

        val zone = ZoneId.systemDefault()

        val now = Instant.now()

        val start = if (date == LocalDate.now()) {
            now.minus(Duration.ofHours(1))
        } else {
            date.atTime(8, 0)
                .atZone(zone)
                .toInstant()
        }

        val end = if (date == LocalDate.now()) {
            now
        } else {
            start.plus(Duration.ofHours(1))
        }

        client.insertRecords(
            listOf(
                StepsRecord(
                    count = steps,
                    startTime = start,
                    endTime = end,
                    startZoneOffset = zone.rules.getOffset(start),
                    endZoneOffset = zone.rules.getOffset(end),
                    metadata = Metadata.manualEntry()
                )
            )
        )
    }

    suspend fun insertTodaySleep(
        sleepHours: Long = 8
    ) {
        insertSleep(
            LocalDate.now(),
            sleepHours
        )
    }
    private suspend fun insertSleep(
        date: LocalDate,
        sleepHours: Long
    ) {

        val zone = ZoneId.systemDefault()

        if (date == LocalDate.now()) {

            val end = Instant.now()
            val start = end.minus(Duration.ofHours(sleepHours))

            client.insertRecords(
                listOf(
                    SleepSessionRecord(
                        startTime = start,
                        endTime = end,
                        startZoneOffset = zone.rules.getOffset(start),
                        endZoneOffset = zone.rules.getOffset(end),
                        metadata = Metadata.manualEntry()
                    )
                )
            )

            return
        }

        val end = date
            .plusDays(1)
            .atTime(7, 0)
            .atZone(zone)

        val start = end.minusHours(sleepHours)

        client.insertRecords(
            listOf(
                SleepSessionRecord(
                    startTime = start.toInstant(),
                    endTime = end.toInstant(),
                    startZoneOffset = start.offset,
                    endZoneOffset = end.offset,
                    metadata = Metadata.manualEntry()
                )
            )
        )
    }
    suspend fun insertTodayHeartRate(
        bpm: Long = 72
    ) {
        insertHeartRate(
            LocalDate.now(),
            bpm
        )
    }



    private suspend fun insertHeartRate(
        date: LocalDate,
        bpm: Long
    ) {

        val zone = ZoneId.systemDefault()
        val now = Instant.now()

        val end = if (date == LocalDate.now()) {
            now
        } else {
            date.atTime(12, 0)
                .atZone(zone)
                .toInstant()
        }

        val start = end.minus(Duration.ofMinutes(5))

        client.insertRecords(
            listOf(
                HeartRateRecord(
                    startTime = start,
                    endTime = end,
                    startZoneOffset = zone.rules.getOffset(start),
                    endZoneOffset = zone.rules.getOffset(end),
                    samples = listOf(
                        Sample(
                            time = end,
                            beatsPerMinute = bpm
                        )
                    ),
                    metadata = Metadata.manualEntry()
                )
            )
        )
    }


    suspend fun insertTodayWeight(
        weightKg: Double = 102.0
    ) {
        insertWeight(
            LocalDate.now(),
            weightKg
        )
    }
    private suspend fun insertWeight(
        date: LocalDate,
        weightKg: Double
    ) {

        val zone = ZoneId.systemDefault()

        val now = Instant.now()

        val time = if (date == LocalDate.now()) {
            now
        } else {
            date.atTime(8, 30)
                .atZone(zone)
                .toInstant()
        }

        client.insertRecords(
            listOf(
                WeightRecord(
                    time = time,
                    zoneOffset = zone.rules.getOffset(time),
                    weight = Mass.kilograms(weightKg),
                    metadata = Metadata.manualEntry()
                )
            )
        )
    }

    suspend fun seedWeek() {




        val lastCompleteDay = LocalDate.now().minusDays(1)

        val steps = listOf(
            7200L,
            8100L,
            9600L,
            6800L,
            10400L,
            12300L,
            8500L
        )

        val sleep = listOf(
            7L,
            8L,
            7L,
            6L,
            8L,
            8L,
            7L
        )

        val heart = listOf(
            72L,
            71L,
            69L,
            73L,
            68L,
            67L,
            70L
        )

        val weight = listOf(
            102.6,
            102.5,
            102.4,
            102.3,
            102.2,
            102.1,
            102.0
        )

        for (i in 0..6) {

            val date = lastCompleteDay.minusDays((6 - i).toLong())



            insertSteps(date, steps[i])

            insertSleep(date, sleep[i])

            insertHeartRate(date, heart[i])

            insertWeight(date, weight[i])

            Log.d(
                "HealthSeeder",
                "Inserted data for $date"
            )
        }
        Log.d("HealthSeeder", "Weekly demo data inserted.")
    }



    suspend fun clearAllDemoData() {

        val timeRange = TimeRangeFilter.between(
            Instant.EPOCH,
            Instant.now().plus(Duration.ofDays(1))
        )

        client.deleteRecords<StepsRecord>(
            timeRangeFilter = timeRange
        )

        client.deleteRecords<SleepSessionRecord>(
            timeRangeFilter = timeRange
        )

        client.deleteRecords<HeartRateRecord>(
            timeRangeFilter = timeRange
        )

        client.deleteRecords<WeightRecord>(
            timeRangeFilter = timeRange
        )

        Log.d(
            "HealthSeeder",
            "All demo data cleared."
        )
    }
}