package com.example.dozer.machine.data.network

import retrofit2.http.GET

interface MachineApiService {
    @GET("Machines")
    suspend fun getIndex(): MachineResult
}