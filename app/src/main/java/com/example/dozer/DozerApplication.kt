package com.example.dozer

import android.app.Application
import android.content.Context
import com.example.dozer.machine.data.MachineRepository
import com.example.dozer.machine.data.NetworkMachineRepository
import com.example.dozer.machine.data.OfflineFirstMachineRepository
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
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


val databaseModule = module {
    single<DozerDatabase> { DozerDatabase.getDatabase(get()) }
    single<MachineDao> { get<DozerDatabase>().getMachineDao() }
}

val networkModule = module {
    single<OkHttpClient> {
        val inputStream: InputStream = get<Context>().resources.openRawResource(R.raw.server_dev)
        val certificate = CertificateFactory.getInstance("X.509").generateCertificate(inputStream)
        inputStream.close()

        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", certificate)

        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)

        val trustManagers: Array<TrustManager> = trustManagerFactory.trustManagers
        val x509TrustManager = trustManagers[0] as X509TrustManager

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustManagerFactory.trustManagers, null)

        OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, x509TrustManager)
            .hostnameVerifier { hostname, _ -> hostname == "10.0.2.2" || hostname == "localhost" }
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://10.0.2.2:5001/api/")
            .client(get<OkHttpClient>())
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    single<MachineApiService> { get<Retrofit>().create(MachineApiService::class.java) }
}

val repositoryModule = module {
    //factory<MachineRepository> { MockMachineRepository() }
    //factory<MachineRepository> { OfflineMachineRepository(get()) }
    //factory<MachineRepository> { NetworkMachineRepository(get()) }
    factory<MachineRepository> { OfflineFirstMachineRepository(get(), get()) }
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