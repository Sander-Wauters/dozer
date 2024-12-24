package com.example.dozer.machine.data.network

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.dozer.DozerDatabase
import com.example.dozer.machine.data.asEntity
import com.example.dozer.machine.data.local.MachineEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MachineRemoteMediator(
    private val database: DozerDatabase,
    private val machineApiService: MachineApiService
): RemoteMediator<Int, MachineEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MachineEntity>
    ): MediatorResult {
        return try {
            val page: Long = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null)
                        1
                    else
                        (lastItem.id / state.config.pageSize) + 1
                }
            }
            val machineResult = machineApiService.getMachines(page.toInt(), state.config.pageSize)
            database.withTransaction {
                if (loadType == LoadType.REFRESH)
                    database.getMachineDao().clearAll()
                database.getMachineDao().upsertAll(machineResult.machines?.map(MachineDto::asEntity) ?: emptyList())
            }
            MediatorResult.Success(endOfPaginationReached = machineResult.machines.isNullOrEmpty() || (machineResult.totalAmount <= state.config.pageSize ))
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}