package io.github.srikantakumar87.biopilot.core.camera

import io.github.srikantakumar87.biopilot.core.camera.model.HRVResult
import io.github.srikantakumar87.biopilot.core.camera.model.RRInterval
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.pow
import kotlin.math.sqrt

@Singleton
class HRVCalculator @Inject constructor() {

    fun calculate(
        intervals: List<RRInterval>
    ): HRVResult? {

        if (intervals.size < 2)
            return null

        val rr = intervals
            .map { it.intervalMillis.toDouble() }

        //---------------------------------------
        // Mean RR
        //---------------------------------------

        val meanRR =
            rr.average()

        //---------------------------------------
        // Mean Heart Rate
        //---------------------------------------

        val meanHeartRate =
            60000.0 / meanRR

        //---------------------------------------
        // SDNN
        //---------------------------------------

        val variance =
            rr.sumOf {

                (it - meanRR).pow(2)

            } / rr.size

        val sdnn =
            sqrt(variance)

        //---------------------------------------
        // RMSSD
        //---------------------------------------

        val successiveDiffs =
            rr.zipWithNext { a, b ->

                b - a
            }

        val rmssd =
            sqrt(

                successiveDiffs.sumOf {

                    it.pow(2)

                } / successiveDiffs.size
            )

        //---------------------------------------
        // pNN50
        //---------------------------------------

        val nn50 =
            successiveDiffs.count {

                kotlin.math.abs(it) > 50.0
            }

        val pnn50 =
            nn50 * 100.0 /
                    successiveDiffs.size

        //---------------------------------------

        return HRVResult(

            rmssd = rmssd,

            sdnn = sdnn,

            pnn50 = pnn50,

            meanRR = meanRR,

            meanHeartRate = meanHeartRate,

            intervalCount = rr.size,

            valid = rr.size >= 20
        )
    }
}