package com.example.workouttracker.ui.calendarScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.YearMonth


@RequiresApi(Build.VERSION_CODES.O)
class CalendarViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    fun resetSelectedDay() {
        _uiState.value = _uiState.value.copy(
            selectedDay = _uiState.value.currentDate.dayOfMonth,
            selectedMonth = _uiState.value.currentDate.monthValue,
            selectedYear = _uiState.value.currentDate.year
        )

        updateMonthDetails()
    }

    fun updateSelectedDay(day: Int) {
        Log.d("CalendarViewModel", "Updating selected day to $day")
        _uiState.value = _uiState.value.copy(selectedDay = day)
    }

    fun updateSelectedMonth(month: Int) {
        Log.d("CalendarViewModel", "Updating selected month to $month")
        _uiState.value = _uiState.value.copy(selectedMonth = month)
    }

    fun updateSelectedYear(year: Int) {
        Log.d("CalendarViewModel", "Updating selected year to $year")
        _uiState.value = _uiState.value.copy(selectedYear = year)
    }

    fun updateSelectedMonthForward() {
        if (_uiState.value.selectedMonth == 12) {
            Log.d("CalendarViewModel", "Updating selected month to 1 and year to ${_uiState.value.selectedYear + 1}")
            _uiState.value = _uiState.value.copy(
                selectedMonth = 1,
                selectedYear = _uiState.value.selectedYear + 1 )
        } else {
            Log.d("CalendarViewModel", "Updating selected month to ${_uiState.value.selectedMonth + 1}")
            _uiState.value = _uiState.value.copy(selectedMonth = _uiState.value.selectedMonth + 1)
        }

        // Update month details, as they are not updated automatically
        updateMonthDetails()

        // Check if the selected day is greater than the number of days in the selected month
        // If yes, set the selected day to the last day of the selected month (number of days in the selected month)
        checkSelectedDay()
    }

    fun updateSelectedMonthBackward() {
        if (_uiState.value.selectedMonth == 1) {
            Log.d("CalendarViewModel", "Updating selected month to 12 and year to ${_uiState.value.selectedYear - 1}")
            _uiState.value = _uiState.value.copy(
                selectedMonth = 12,
                selectedYear = _uiState.value.selectedYear - 1 )
        } else {
            Log.d("CalendarViewModel", "Updating selected month to ${_uiState.value.selectedMonth - 1}")
            _uiState.value = _uiState.value.copy(selectedMonth = _uiState.value.selectedMonth - 1)
        }

        // Update month details, as they are not updated automatically
        updateMonthDetails()

        // Check if the selected day is greater than the number of days in the selected month
        // If yes, set the selected day to the last day of the selected month (number of days in the selected month)
        checkSelectedDay()
    }

    private fun checkSelectedDay() {
        if(_uiState.value.selectedDay > _uiState.value.selectedMonthNumberOfDays) {
            Log.d("CalendarViewModel","selectedDay > selectedMonthNumberOfDays, setting selectedDay (${_uiState.value.selectedDay}) to selectedMonthNumberOfDays: ${_uiState.value.selectedMonthNumberOfDays}")
            _uiState.value = _uiState.value.copy(selectedDay = _uiState.value.selectedMonthNumberOfDays)
        } else { Log.d("CalendarViewModel","selectedDay <= selectedMonthNumberOfDays, keeping selectedDay: ${_uiState.value.selectedDay}") }
    }

    private fun updateMonthDetails() {
        Log.d("CalendarViewModel", "Updating month details for month: ${_uiState.value.selectedMonth}, year: ${_uiState.value.selectedYear}")
        _uiState.value = _uiState.value.copy(
            selectedMonthNumberOfDays = YearMonth.of(_uiState.value.selectedYear, _uiState.value.selectedMonth).lengthOfMonth(),
            selectedMonthFirstDay = LocalDate.of(_uiState.value.selectedYear, _uiState.value.selectedMonth, 1).dayOfWeek.value,
            selectedMonthFirstDayIndex = LocalDate.of(_uiState.value.selectedYear, _uiState.value.selectedMonth, 1).dayOfWeek.value - 1
        )
        Log.d("CalendarViewModel", "Month details updated to selectedMonthNumberOfDays: ${_uiState.value.selectedMonthNumberOfDays}, selectedMonthFirstDay: ${_uiState.value.selectedMonthFirstDay}, selectedMonthFirstDayIndex: ${_uiState.value.selectedMonthFirstDayIndex}")
    }



}