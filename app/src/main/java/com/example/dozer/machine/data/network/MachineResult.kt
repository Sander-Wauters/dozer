package com.example.dozer.machine.data.network

import com.example.dozer.machine.data.MachineDto
import kotlinx.serialization.Serializable

@Serializable
data class MachineResult(
    val machines: List<MachineDto>?,
    val totalAmount: Int
)