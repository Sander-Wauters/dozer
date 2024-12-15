package com.example.dozer.machine.data

import com.example.dozer.machine.data.local.MachineDao
import com.example.dozer.machine.data.local.MockMachineDatasource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

interface MachineRepository {
    suspend fun getIndex(): Flow<List<MachineDto.Index>>
}

class MockMachineRepository : MachineRepository {
    override suspend fun getIndex(): Flow<List<MachineDto.Index>> {
        delay(2500)
        return flowOf(MockMachineDatasource().loadMachines())
    }
}

class OfflineMachineRepository(private val machineDao: MachineDao) : MachineRepository {
    override suspend fun getIndex(): Flow<List<MachineDto.Index>> {
        return machineDao.getAll().map { machines ->
            machines.map { machine ->
                machine.toDtoIndex()
            }
        }
    }
}