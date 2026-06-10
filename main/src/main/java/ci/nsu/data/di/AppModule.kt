package ci.nsu.data.di

import ci.nsu.data.local.AppDatabase
import ci.nsu.data.local.SessionManager
import ci.nsu.data.remote.AuthApiService
import ci.nsu.data.remote.AuthInterceptor
import ci.nsu.data.repository.DepositRepository
import ci.nsu.data.repository.DepositRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    // --- ЛОКАЛЬНЫЙ СЛОЙ (Уже было реализовано) ---
    single { SessionManager(androidContext()) }
    single { AppDatabase.getDatabase(androidContext()) }
    single { get<AppDatabase>().depositDao() }
    single<DepositRepository> { DepositRepositoryImpl(get()) }

    // --- СЕТЕВОЙ СЛОЙ (Новое) ---

    // Создаем интерцептор для авторизации
    single { AuthInterceptor(get()) }

    // Создаем логировщик запросов, чтобы видеть сетевой обмен в Logcat
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // Настраиваем OkHttpClient и добавляем наши интерцепторы
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>()) // Автоматически подставит токен
            .addInterceptor(get<HttpLoggingInterceptor>()) // Покажет логи запросов
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Настраиваем сам Retrofit
    single {
        Retrofit.Builder()
            // ВНИМАНИЕ: Замените URL на адрес вашего реального сервера (локального или удаленного)
            .baseUrl("http://10.0.2") // 10.0.2.2 — адрес хоста для эмулятора Android
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Создаем реализацию нашего интерфейса запросов
    single { get<Retrofit>().create(AuthApiService::class.java) }
}
