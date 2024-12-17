package com.example.dozer

import android.app.Application
import com.example.dozer.machine.data.MachineRepository
import com.example.dozer.machine.data.MockMachineRepository
import com.example.dozer.machine.data.NetworkMachineRepository
import com.example.dozer.machine.data.OfflineMachineRepository
import com.example.dozer.machine.data.local.MachineDao
import com.example.dozer.machine.data.network.MachineApiService
import com.example.dozer.machine.ui.MachineViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val databaseModule = module {
    single<DozerDatabase> { DozerDatabase.getDatabase(get()) }
    single<MachineDao> { get<DozerDatabase>().getMachineDao() }
}

val networkModule = module {
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://10.0.2.2:5000/api/")
            .client(get<OkHttpClient>())
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    single<MachineApiService> { get<Retrofit>().create(MachineApiService::class.java) }
}

val repositoryModule = module {
    //factory<MachineRepository> { MockMachineRepository() }
    //factory<MachineRepository> { OfflineMachineRepository(get()) }
    factory<MachineRepository> { NetworkMachineRepository(get()) }
}

val viewModelModule = module {
    viewModel<MachineViewModel> { MachineViewModel(get()) }
}

class DozerApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@DozerApplication)
            modules(databaseModule, networkModule, repositoryModule, viewModelModule)
        }
    }
}