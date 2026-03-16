package com.rashmi.birthdayreminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashmi.birthdayreminder.domain.model.BirthdayData
import com.rashmi.birthdayreminder.domain.model.UiEvent
import com.rashmi.birthdayreminder.domain.usecase.IBirthdayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val birthdayUseCase: IBirthdayUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(BirthdayData())
    val uiState: StateFlow<BirthdayData> = _uiState.asStateFlow()
    val formatter = DateTimeFormatter.ofPattern("ddMMyyyy")

    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

    val birthdays = birthdayUseCase.getSortedBirthdayList().stateIn(
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
            birthdayUseCase.addBirthday(_uiState.value)
            _events.emit(UiEvent.BirthdayAdded)
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

    fun deleteBirthday(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            birthdayUseCase.deleteBirthday(id)
        }
    }

}