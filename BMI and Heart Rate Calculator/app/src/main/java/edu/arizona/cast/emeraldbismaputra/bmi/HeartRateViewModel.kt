package edu.arizona.cast.emeraldbismaputra.bmi

import androidx.lifecycle.ViewModel

open class HeartRateViewModel: ViewModel() {
    private var maximumHeartRate: Int = 0
    private var minTargetHeartRate: Int = 0
    private var maxTargetHeartRate: Int = 0

    fun maxHeartRate(age: Int): Int {
        maximumHeartRate = 220 - age
        return maximumHeartRate
    }

    fun returnMaxHeartRate(): Int {
        return maximumHeartRate
    }

    fun minTargetRate(maximumHeartRate: Int): Int {
        minTargetHeartRate = (maximumHeartRate * 50) / 100
        return minTargetHeartRate
    }

    fun returnMinTargetRate(): Int {
        return minTargetHeartRate
    }

    fun maxTargetRate(maximumHeartRate: Int): Int {
        maxTargetHeartRate = (maximumHeartRate * 85) / 100
        return maxTargetHeartRate
    }

    fun returnMaxTargetRate(): Int {
        return maxTargetHeartRate
    }
}