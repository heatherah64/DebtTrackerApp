package com.example.debttracker.ui

data class TrackerUiState(
    val name1: String = "",
    val name2: String = "",
    val name3: String = "",
    val name4: String = "",
    val name5: String = "",
    val currentDebt1: Double = 0.0,
    val currentDebt2: Double = 0.0,
    val currentDebt3: Double = 0.0,
    val currentDebt4: Double = 0.0,
    val currentDebt5: Double = 0.0,
    val totalDebt: Double = 0.0
)