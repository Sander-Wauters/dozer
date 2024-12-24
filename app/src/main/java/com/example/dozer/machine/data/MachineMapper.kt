package com.example.dozer.machine.data

import com.example.dozer.machine.data.local.MachineEntity
import com.example.dozer.machine.data.network.MachineDto

fun Machine.asEntity(): MachineEntity {
    return MachineEntity(
        id = id.toLong(),
        brand = brand,
        name = name,
        serialNumber = serialNumber,
        type = type ?: "",
        description = description,
        imageUrl = imageUrl,
    )
}

fun Machine.asDto(): MachineDto {
    return MachineDto(
        id = id,
        brand = brand,
        name = name,
        serialNumber = serialNumber,
        type = type,
        description = description,
        imageUrl = imageUrl,
    )
}

fun MachineEntity.asExternalModel(): Machine {
    return Machine(
        id = id.toInt(),
        brand = brand,
        name = name,
        serialNumber = serialNumber,
        type = type,
        description = description,
        imageUrl = imageUrl,
    )
}

fun MachineEntity.asDto(): MachineDto {
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

fun MachineDto.asEntity(): MachineEntity {
    return MachineEntity(
        id = id.toLong(),
        brand = brand,
        name = name,
        serialNumber = serialNumber,
        type = type ?: "",
        description = description,
        imageUrl = imageUrl,
    )
}

fun MachineDto.asExternalModel(): Machine {
    return Machine(
        id = id,
        brand = brand,
        name = name,
        serialNumber = serialNumber,
        type = type,
        description = description,
        imageUrl = imageUrl,
    )
}
