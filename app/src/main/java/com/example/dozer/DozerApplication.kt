package com.example.dozer

import android.app.Application
import com.example.dozer.machine.data.MachineRepository
import com.example.dozer.machine.data.MockMachineRepository
import com.example.dozer.machine.data.OfflineMachineRepository
import com.example.dozer.machine.data.local.MachineDao
import com.example.dozer.machine.ui.MachineViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

val databaseModule = module {
    single<DozerDatabase> { DozerDatabase.getDatabase(get()) }
    single<MachineDao> { get<DozerDatabase>().getMachineDao() }
}

val repositoryModule = module {
    factory<MachineRepository> { MockMachineRepository() }
    //factory<MachineRepository> { OfflineMachineRepository(get()) }
}

val viewModelModule = module {
    viewModel { MachineViewModel(get()) }
}

class DozerApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@DozerApplication)
            modules(databaseModule, repositoryModule, viewModelModule)
        }
    }
}