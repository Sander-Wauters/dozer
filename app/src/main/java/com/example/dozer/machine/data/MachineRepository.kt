package com.example.dozer.machine.data

import com.example.dozer.machine.data.local.MachineDao
import com.example.dozer.machine.data.local.MockMachineDatasource
import com.example.dozer.machine.data.network.MachineApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

interface MachineRepository {
    suspend fun getAll(): Flow<List<MachineDto>>
    suspend fun insert(machine: MachineDto)
}

class MockMachineRepository : MachineRepository {
    override suspend fun getAll(): Flow<List<MachineDto>> {
        delay(2500)
        return flowOf(MockMachineDatasource().loadMachines())
    }

    override suspend fun insert(machine: MachineDto) {
        TODO("Not yet implemented")
    }
}

class OfflineMachineRepository(private val machineDao: MachineDao) : MachineRepository {
    override suspend fun getAll(): Flow<List<MachineDto>> {
        return machineDao.getAll().map { machines ->
            machines.map { machine ->
                machine.toDtoIndex()
            }
        }
    }

    override suspend fun insert(machine: MachineDto) {
        machineDao.insert(machine.toMachine())
    }
}

class NetworkMachineRepository(private val machineApiService: MachineApiService) : MachineRepository {
    override suspend fun getAll(): Flow<List<MachineDto>> {
        return flowOf(machineApiService.getIndex().machines ?: emptyList())
    }

    override suspend fun insert(machine: MachineDto) {
        TODO("Not yet implemented")
    }

}