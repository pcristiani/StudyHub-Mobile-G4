package com.example.compose.studyhub.domain


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue

///
open class DateTimeState(
    private val dayValidator: (String) -> Boolean = { it.toIntOrNull() in 1..31 },
    private val monthValidator: (String) -> Boolean = {it.toIntOrNull() in 1..12},
    private val yearValidator: (String) -> Boolean = {it.toIntOrNull() in 1900.. 2200},
    private val errorForDay: (String) -> String = { "Invalid day" },
    private val errorForMonth: (String) -> String = {"Invalid month"},
    private val errorForYear: (String) -> String = {"Invalid year"},
) {
    var day: String by mutableStateOf("")
    var month: String by mutableStateOf("")
    var year: String by mutableStateOf("")
    // El campo de texto siempre se centró
    var isFocusedDirty: Boolean by mutableStateOf(false)
    var isFocused: Boolean by mutableStateOf(false)
    private var displayErrors: Boolean by mutableStateOf(false)

    open val isValid: Boolean
        get() = dayValidator(day) && monthValidator(month) && yearValidator(year)

    fun onFocusChange(focused: Boolean) {
        isFocused = focused
        if (focused) isFocusedDirty = true
    }

    fun enableShowErrors() {
        // Solo muestra errores si el texto estaba al menos una vez enfocado
        if (isFocusedDirty) {
            displayErrors = true
        }
    }

    fun showErrors() = !isValid && displayErrors

    fun getError(): String? {
        return when {
            !dayValidator(day) -> errorForDay(day)
            !monthValidator(month) -> errorForMonth(month)
            !yearValidator(year) -> errorForYear(year)
            else -> null
        }
    }
}

///
fun dateTimeStateSaver(state: DateTimeState) =
    listSaver<DateTimeState, Any>(
        save = { listOf(it.day, it.month, it.year, it.isFocusedDirty) },
        restore = {
            state.apply {
                day = it[0] as String
                month = it[1] as String
                year = it[2] as String
                isFocusedDirty = it[3] as Boolean
            }
        }
    )
