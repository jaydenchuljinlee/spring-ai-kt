package com.example.ai.tool.dto

import java.util.function.Function

class FunctionWeather: Function<WeatherRequest, WeatherResponse> {
    override fun apply(req: WeatherRequest): WeatherResponse {
        return WeatherResponse(30.0, req.unit) // ✅ 이제 req는 유효
    }
}