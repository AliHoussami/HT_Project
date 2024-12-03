package com.example.ht

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class WaterTrackerViewModel : ViewModel() {

    private val _waterIntake = MutableLiveData(0)
    val waterIntake: LiveData<Int> get() = _waterIntake

    private val _dailyGoal = MutableLiveData(2000)
    val dailyGoal: LiveData<Int> get() = _dailyGoal

    private val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    private var lastDate = sdf.format(Date())

    init {
        resetDailyIntakeIfNeeded()
    }

    fun addWater(amount: Int) {
        _waterIntake.value = (_waterIntake.value ?: 0) + amount
    }

    fun resetWaterIntake() {
        _waterIntake.value = 0
    }

    fun setDailyGoal(goal: Int) {
        _dailyGoal.value = goal
    }

    private fun resetDailyIntakeIfNeeded() {
        val currentDate = sdf.format(Date())
        if (currentDate != lastDate) {
            _waterIntake.value = 0
            lastDate = currentDate
        }
    }
}