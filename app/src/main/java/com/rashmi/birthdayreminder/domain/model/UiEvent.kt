package com.rashmi.birthdayreminder.domain.model

sealed class UiEvent {
    data object BirthdayAdded : UiEvent()
}