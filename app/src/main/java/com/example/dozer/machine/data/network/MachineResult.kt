package com.example.dozer.machine.data.network

import kotlinx.serialization.Serializable

@Serializable
data class MachineResult(
    val machines: List<MachineDto>?,
    val totalAmount: Int
)