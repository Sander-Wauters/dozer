package com.example.dozer.machine.data

import com.example.dozer.machine.data.local.MachineDao
import com.example.dozer.machine.data.local.MachineEntity
import com.example.dozer.machine.data.local.MockMachineDatasource
import com.example.dozer.machine.data.network.MachineApiService
import com.example.dozer.machine.data.network.MachineDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

interface MachineRepository {
    suspend fun getAll(): Flow<List<Machine>>
}

class MockMachineRepository : MachineRepository {
    override suspend fun getAll(): Flow<List<Machine>> {
        delay(2500)
        return flowOf(MockMachineDatasource().loadMachines())
    }
}

class OfflineMachineRepository(private val machineDao: MachineDao) : MachineRepository {
    override suspend fun getAll(): Flow<List<Machine>> {
        return machineDao.getAll().map { it.map(MachineEntity::asExternalModel) }
    }

    suspend fun insert(machine: Machine) {
        machineDao.insert(machine.asEntity())
    }
}

class NetworkMachineRepository(private val machineApiService: MachineApiService) : MachineRepository {
    override suspend fun getAll(): Flow<List<Machine>> {
        return flowOf(machineApiService.getIndex().machines?.map(MachineDto::asExternalModel) ?: emptyList())
    }
}

class OfflineFirstMachineRepository(
    private val machineDao: MachineDao,
    private val machineApiService: MachineApiService
) : MachineRepository {
    override suspend fun getAll(): Flow<List<Machine>> {
        return machineDao.getAll().map { it.map(MachineEntity::asExternalModel) }
    }
}