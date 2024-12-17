package com.example.dozer.machine.data

import kotlinx.serialization.Serializable

@Serializable
data class MachineDto(
    val id: Int,
    val brand: String,
    val name: String,
    val serialNumber: String,
    val type: String?,
    val description: String?,
    val imageUrl: String?
)
