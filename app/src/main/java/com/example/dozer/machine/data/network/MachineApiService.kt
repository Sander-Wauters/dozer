package com.example.dozer.machine.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface MachineApiService {
    @GET("Machine")
    suspend fun getMachines(
        @Query("Page") page: Int = 1,
        @Query("PageSize") pageSize: Int = 10
    ): MachineResult
}