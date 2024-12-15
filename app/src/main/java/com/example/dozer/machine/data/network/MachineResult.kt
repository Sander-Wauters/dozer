package com.example.dozer.machine.data.network

import com.example.dozer.machine.data.MachineDto

abstract class MachineResult {
    data class Index(
        val machines: List<MachineDto.Index>?,
        val totalAmount: Int
    )
}