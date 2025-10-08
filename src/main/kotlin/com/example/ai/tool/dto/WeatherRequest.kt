package com.example.ai.tool.dto

data class WeatherRequest(
    val location: String,
    val unit: UnitEnum
)