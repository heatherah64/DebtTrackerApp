package com.example.debttracker.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TrackerViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(TrackerUiState())
    val uiState: StateFlow<TrackerUiState> = _uiState.asStateFlow()

    fun updateName(personNumber: Int, updatedName: String) {
        updateTrackerStateName(personNumber, updatedName)
    }

    fun addAmount(personNumber: Int, debtInput: Double) {
        val updatedDebt = when (personNumber) {
            1 -> _uiState.value.currentDebt1.plus(debtInput)
            2 -> _uiState.value.currentDebt2.plus(debtInput)
            3 -> _uiState.value.currentDebt3.plus(debtInput)
            4 -> _uiState.value.currentDebt4.plus(debtInput)
            else -> _uiState.value.currentDebt5.plus(debtInput)
        }
        updateTrackerStateDebt(personNumber, updatedDebt)
    }

    fun subtractAmount(personNumber: Int, debtInput: Double) {
        val updatedDebt = when (personNumber) {
            1 -> _uiState.value.currentDebt1.minus(debtInput)
            2 -> _uiState.value.currentDebt2.minus(debtInput)
            3 -> _uiState.value.currentDebt3.minus(debtInput)
            4 -> _uiState.value.currentDebt4.minus(debtInput)
            else -> _uiState.value.currentDebt5.minus(debtInput)
        }
        updateTrackerStateDebt(personNumber, updatedDebt)
    }

    private fun updateTrackerStateDebt(personNumber: Int, updatedDebt: Double) {
        _uiState.update { currentState ->
            when (personNumber) {
                1 -> currentState.copy(
                    currentDebt1 = updatedDebt
                )
                2 -> currentState.copy(
                    currentDebt2 = updatedDebt
                )
                3 -> currentState.copy(
                    currentDebt3 = updatedDebt
                )
                4 -> currentState.copy(
                    currentDebt4 = updatedDebt
                )
                else -> currentState.copy(
                    currentDebt5 = updatedDebt
                )
            }
        }
    }

    private fun updateTrackerStateName(personNumber: Int, updatedName: String) {
        _uiState.update { currentState ->
            when (personNumber) {
                1 -> currentState.copy(
                    name1 = updatedName
                )
                2 -> currentState.copy(
                    name2 = updatedName
                )
                3 -> currentState.copy(
                    name3 = updatedName
                )
                4 -> currentState.copy(
                    name4 = updatedName
                )
                else -> currentState.copy(
                    name5 = updatedName
                )
            }
        }
    }
}