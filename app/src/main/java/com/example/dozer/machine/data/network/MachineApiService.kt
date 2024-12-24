package com.example.dozer.machine.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface MachineApiService {
    @GET("Machine")
    suspend fun getMachines(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 25
    ): MachineResult
}