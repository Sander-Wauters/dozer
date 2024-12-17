package com.example.dozer.machine.data

import com.example.dozer.machine.data.local.Machine

fun MachineDto.toMachine(): Machine {
    return Machine(
        id = id.toLong(),
        brand = brand,
        name = name,
        serialNumber = serialNumber,
        type = type ?: "",
        description = description,
        imageUrl = imageUrl,
    )
}

fun Machine.toDtoIndex(): MachineDto {
    return MachineDto(
        id = id.toInt(),
        brand = brand,
        name = name,
        serialNumber = serialNumber,
        type = type,
        description = description,
        imageUrl = imageUrl,
    )
}