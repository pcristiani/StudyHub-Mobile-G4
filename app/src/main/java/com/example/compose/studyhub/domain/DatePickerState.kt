package com.example.compose.studyhub.domain

import android.os.Build
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun datePicker(): DatePickerState {
    return rememberDatePickerState(
        initialDisplayedMonthMillis = System.currentTimeMillis(),
        yearRange = 1900..2025,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val dayOfWeek = Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneId.of("UTC"))
                        .toLocalDate().dayOfWeek
                    dayOfWeek != DayOfWeek.SUNDAY && dayOfWeek != DayOfWeek.SATURDAY
                } else {
                    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    calendar.timeInMillis = utcTimeMillis
                    calendar[Calendar.DAY_OF_WEEK] != Calendar.SUNDAY &&
                            calendar[Calendar.DAY_OF_WEEK] != Calendar.SATURDAY
                }
            }

            override fun isSelectableYear(year: Int): Boolean {
                return true
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
fun formatDate(datePickerState: DatePickerState, pattern: String = "yyyy-MM-dd"): String {
    val selectedDateMillis = datePickerState.selectedDateMillis
    return if (selectedDateMillis != null) {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        val date = Date(selectedDateMillis)
        sdf.format(date)
    } else {
        ""
    }
}