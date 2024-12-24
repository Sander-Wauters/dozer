package com.example.dozer.machine.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MachineDao {
    @Query("SELECT * from machines")
    fun getAll(): Flow<List<MachineEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(machineEntity: MachineEntity)
}