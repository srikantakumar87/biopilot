package io.github.srikantakumar87.biopilot.core.ai


import io.github.srikantakumar87.biopilot.core.ai.model.HealthAnalysis
import io.github.srikantakumar87.biopilot.core.ai.model.HealthInsight
import io.github.srikantakumar87.biopilot.core.ai.model.HealthSnapshot
import io.github.srikantakumar87.biopilot.core.ai.model.InsightIcon
import io.github.srikantakumar87.biopilot.core.ai.model.InsightSeverity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsightGenerator @Inject constructor() {

    fun generate(
        snapshot: HealthSnapshot,
        analysis: HealthAnalysis
    ): List<HealthInsight> {

        val insights = mutableListOf<HealthInsight>()

        addActivityInsight(
            insights,
            analysis
        )

        addSleepInsight(
            insights,
            snapshot
        )

        addHeartInsight(
            insights,
            snapshot
        )

        addWeightInsight(
            insights,
            snapshot
        )

        addOverallInsight(
            insights,
            analysis
        )

        return insights
    }

    private fun addActivityInsight(
        insights: MutableList<HealthInsight>,
        analysis: HealthAnalysis
    ) {

        if (analysis.stepScore >= 80) {

            insights += HealthInsight(
                title = "Excellent Activity",
                message = "You achieved your daily activity goal.",
                severity = InsightSeverity.GOOD,
                metric = "${analysis.stepScore}/100",
                icon = InsightIcon.ACTIVITY
            )

        } else {

            insights += HealthInsight(
                title = "Increase Activity",
                message = "Walking a little more today will improve your health score.",
                severity = InsightSeverity.WARNING,
                metric = "${analysis.stepScore}/100",
                icon = InsightIcon.ACTIVITY
            )
        }
    }

    private fun addSleepInsight(
        insights: MutableList<HealthInsight>,
        snapshot: HealthSnapshot
    ) {

        val averageSleepHours = snapshot.averageSleepHours

        if (averageSleepHours >= 7.0) {

            insights += HealthInsight(
                title = "Healthy Sleep",
                message = "Your recent sleep duration is within the recommended range.",
                severity = InsightSeverity.GOOD,
                metric = "%.1f h".format(averageSleepHours),
                icon = InsightIcon.SLEEP
            )

        } else {

            insights += HealthInsight(
                title = "Sleep Deficit",
                message = "Aim for 7–9 hours of sleep consistently.",
                severity = InsightSeverity.WARNING,
                metric = "%.1f h".format(averageSleepHours),
                icon = InsightIcon.SLEEP
            )
        }
    }

    private fun addHeartInsight(
        insights: MutableList<HealthInsight>,
        snapshot: HealthSnapshot
    ) {

        val latestHeartRate = snapshot.latestHeartRate

        if (latestHeartRate == null) return

        if (latestHeartRate in 55..90) {

            insights += HealthInsight(
                title = "Heart Rate",
                message = "Your latest heart rate appears normal.",
                severity = InsightSeverity.GOOD,
                metric = "$latestHeartRate bpm",
                icon = InsightIcon.HEART
            )

        } else {

            insights += HealthInsight(
                title = "Heart Rate",
                message = "Heart rate is outside the usual resting range.",
                severity = InsightSeverity.INFO,
                metric = "$latestHeartRate bpm",
                icon = InsightIcon.HEART
            )
        }
    }

    private fun addWeightInsight(
        insights: MutableList<HealthInsight>,
        snapshot: HealthSnapshot
    ) {

        val weight = snapshot.weight
        val bmi = snapshot.bmi

        if (weight == null || bmi == null) return

        when {

            bmi < 18.5 ->

                insights += HealthInsight(
                    title = "BMI",
                    message = "You are below the recommended BMI range.",
                    severity = InsightSeverity.INFO,
                    metric = "BMI %.1f".format(bmi),
                    icon = InsightIcon.WEIGHT
                )

            bmi < 25 ->

                insights += HealthInsight(
                    title = "Healthy Weight",
                    message = "Your BMI is within the healthy range.",
                    severity = InsightSeverity.GOOD,
                    metric = "BMI %.1f".format(bmi),
                    icon = InsightIcon.WEIGHT
                )

            bmi < 30 ->

                insights += HealthInsight(
                    title = "Weight",
                    message = "A small weight reduction could improve long-term health.",
                    severity = InsightSeverity.WARNING,
                    metric = "BMI %.1f".format(bmi),
                    icon = InsightIcon.WEIGHT
                )

            else ->

                insights += HealthInsight(
                    title = "Weight",
                    message = "Gradual weight reduction may reduce cardiovascular risk.",
                    severity = InsightSeverity.WARNING,
                    metric = "BMI %.1f".format(bmi),
                    icon = InsightIcon.WEIGHT
                )
        }
    }

    private fun addOverallInsight(
        insights: MutableList<HealthInsight>,
        analysis: HealthAnalysis
    ) {

        when {

            analysis.overallScore >= 90 ->

                insights += HealthInsight(
                    title = "Excellent Health",
                    message = "Your overall health metrics are excellent today.",
                    severity = InsightSeverity.GOOD,
                    metric = "${analysis.overallScore}/100",
                    icon = InsightIcon.INFO
                )

            analysis.overallScore >= 75 ->

                insights += HealthInsight(
                    title = "Good Progress",
                    message = "You're doing well. Small improvements can make an even bigger difference.",
                    severity = InsightSeverity.INFO,
                    metric = "${analysis.overallScore}/100",
                    icon = InsightIcon.INFO
                )

            else ->

                insights += HealthInsight(
                    title = "Needs Attention",
                    message = "Several health metrics could be improved.",
                    severity = InsightSeverity.WARNING,
                    metric = "${analysis.overallScore}/100",
                    icon = InsightIcon.INFO
                )
        }
    }
}