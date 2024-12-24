package com.example.dozer.machine.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MachineDao {
    @Query("SELECT * from machines")
    fun getAll(): Flow<List<MachineEntity>>

    @Upsert
    suspend fun upsertAll(machines: List<MachineEntity>)

    @Query("DELETE FROM machines")
    suspend fun clearAll()

    @Query("SELECT * FROM machines")
    fun pagingSource(): PagingSource<Int, MachineEntity>
}