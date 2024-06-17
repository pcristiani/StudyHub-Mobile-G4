package com.example.compose.studyhub.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue

///
open class TextFieldState(
        private val validator: (String) -> Boolean = { true },
        private val errorFor: (String) -> String = { "" },

) {
    var text: String by mutableStateOf("")
    // El campo de texto siempre se centró
    var isFocusedDirty: Boolean by mutableStateOf(false)
    var isFocused: Boolean by mutableStateOf(false)
    private var displayErrors: Boolean by mutableStateOf(false)

    open val isValid: Boolean
        get() = validator(text)

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

    open fun getError(): String? {
        return if (showErrors()) {
            errorFor(text)
        } else {
            null
        }
    }
}

///
fun textFieldStateSaver(state: TextFieldState) =
        listSaver<TextFieldState, Any>(
                save = { listOf(it.text, it.isFocusedDirty) },
                restore = {
                    state.apply {
                        text = it[0] as String
                        isFocusedDirty = it[1] as Boolean
                    }
                }
        )
