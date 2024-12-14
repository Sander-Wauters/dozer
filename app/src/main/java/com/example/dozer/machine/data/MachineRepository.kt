package com.example.dozer.machine.data

import kotlinx.coroutines.delay

interface MachineRepository {
    suspend fun getIndex(): MachineResult.Index
}

class MockMachineRepository : MachineRepository {
    override suspend fun getIndex(): MachineResult.Index {
        delay(2500)
        return MachineResult.Index(
            machines = MockMachineDatasource().loadMachines(),
            totalAmount = 100
        )
    }
}