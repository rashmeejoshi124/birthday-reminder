package com.rashmi.birthdayreminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashmi.birthdayreminder.models.BirthdayData
import com.rashmi.birthdayreminder.usecase.BirthdayRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BirthdayRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(BirthdayData())
    val uiState: StateFlow<BirthdayData> = _uiState.asStateFlow()
    val formatter = DateTimeFormatter.ofPattern("ddMMyyyy")

    val birthdays = repository.getBirthdays().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun onNameChange(name: String) {
        _uiState.update {
            it.copy(name = name)
        }
    }

    fun onDateTextChange(input: String) {
        val digits = input.filter { it.isDigit() }.take(8)
        val parsedDate = if (digits.length == 8) {
            try {
                LocalDate.parse(digits, formatter)
            } catch (e: Exception) {
                null
            }
        } else null

        _uiState.update {
            it.copy(
                dateDigits = digits,
                date = parsedDate
            )
        }

    }

    fun onDatePicked(date: LocalDate) {
        val digits = date.format(formatter)
        _uiState.update {
            it.copy(
                dateDigits = digits,
                date = date
            )
        }
    }

    fun addBirthday() {
        val state = _uiState.value
        val name = state.name.trim()
        val date = state.date
        if (name.isBlank() || date == null) {
            return
        }
        viewModelScope.launch {
            repository.insertBirthday(
                name = name,
                date = date
            )
            clearForm()
        }
    }

    private fun clearForm() {
        _uiState.update {
            it.copy(
                name = "",
                dateDigits = "",
                date = null
            )
        }
    }

}