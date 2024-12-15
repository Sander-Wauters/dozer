package com.example.dozer.machine.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface MachineRepository {
    suspend fun getIndex(): Flow<MachineResult.Index>
}

class MockMachineRepository : MachineRepository {
    override suspend fun getIndex(): Flow<MachineResult.Index> {
        delay(2500)
        return flowOf(MachineResult.Index(
            machines = MockMachineDatasource().loadMachines(),
            totalAmount = 100
        ))
    }
}