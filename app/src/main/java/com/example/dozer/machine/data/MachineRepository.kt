package com.example.dozer.machine.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.dozer.DozerDatabase
import com.example.dozer.machine.data.local.MachineDao
import com.example.dozer.machine.data.local.MachineEntity
import com.example.dozer.machine.data.local.MockMachineDatasource
import com.example.dozer.machine.data.network.MachineApiService
import com.example.dozer.machine.data.network.MachineDto
import com.example.dozer.machine.data.network.MachineRemoteMediator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

interface MachineRepository {
    suspend fun getMachines(): Flow<PagingData<Machine>>
}

class MockMachineRepository : MachineRepository {
    override suspend fun getMachines(): Flow<PagingData<Machine>> {
        delay(2500)
        return flowOf(PagingData.from(MockMachineDatasource().loadMachines()))
    }
}

class OfflineMachineRepository(private val machineDao: MachineDao) : MachineRepository {
    override suspend fun getMachines(): Flow<PagingData<Machine>> {
        var machines: List<Machine> = emptyList()
        machineDao.getAll().map { it.map(MachineEntity::asExternalModel) }.collect { machines = it }
        return flowOf(PagingData.from(machines))
    }
}

class NetworkMachineRepository(private val machineApiService: MachineApiService) : MachineRepository {
    override suspend fun getMachines(): Flow<PagingData<Machine>> {
        return flowOf(PagingData.from(machineApiService.getMachines().machines?.map(MachineDto::asExternalModel) ?: emptyList()))
    }
}

@OptIn(ExperimentalPagingApi::class)
class OfflineFirstMachineRepository(
    private val database: DozerDatabase,
    private val machineApiService: MachineApiService
) : MachineRepository {

    private val pager = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        remoteMediator = MachineRemoteMediator(database, machineApiService),
        pagingSourceFactory = { database.getMachineDao().pagingSource() }
    )

    override suspend fun getMachines(): Flow<PagingData<Machine>> {
        return pager.flow.map { pagingData -> pagingData.map(MachineEntity::asExternalModel) }
    }
}