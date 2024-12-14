package com.example.dozer.machine.data

abstract class MachineResult {
    data class Index(
        val machines: List<MachineDto.Index>?,
        val totalAmount: Int
    )
}