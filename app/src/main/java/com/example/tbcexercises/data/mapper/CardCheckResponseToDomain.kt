package com.example.tbcexercises.data.mapper

import com.example.tbcexercises.data.remote.response.CardCheckResponse
import com.example.tbcexercises.domain.model.GetCardCheckStatus
import com.example.tbcexercises.domain.util.CardCheckStatus


fun CardCheckResponse.toDomain(): GetCardCheckStatus = GetCardCheckStatus(
    status = if (this.status.lowercase() == "success") CardCheckStatus.SUCCESS else CardCheckStatus.FAILED
)