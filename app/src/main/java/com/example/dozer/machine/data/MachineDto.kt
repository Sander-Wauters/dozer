package com.example.dozer.machine.data

abstract class MachineDto {
    data class Index(
        val id: Int,
        val brand: String,
        val name: String,
        val serialNumber: String,
        val type: String?,
        val description: String?,
        val imageUrl: String?
    )
}
