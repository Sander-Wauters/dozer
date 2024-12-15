package com.example.dozer.machine.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MachineDao {
    @Query("SELECT * from machines")
    fun getAll(): Flow<List<Machine>>
}