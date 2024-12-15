package com.example.dozer.machine.data

import com.example.dozer.machine.data.local.Machine

fun MachineDto.Index.toMachine(): Machine {
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

fun Machine.toDtoIndex(): MachineDto.Index {
    return MachineDto.Index(
        id = id.toInt(),
        brand = brand,
        name = name,
        serialNumber = serialNumber,
        type = type,
        description = description,
        imageUrl = imageUrl,
    )
}