package ci.nsu.data.di

import ci.nsu.data.local.AppDatabase
import ci.nsu.data.local.SessionManager
import ci.nsu.data.repository.DepositRepository
import ci.nsu.data.repository.DepositRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    // Менеджер сессий (синглтон, нужен контекст приложения)
    single { SessionManager(androidContext()) }

    // База данных Room (синглтон)
    single { AppDatabase.getDatabase(androidContext()) }

    // Предоставляем DepositDao, забирая его из созданной выше базы данных
    single { get<AppDatabase>().depositDao() }

    // Репозиторий вкладов (подставляет DepositDao автоматически через get())
    single<DepositRepository> { DepositRepositoryImpl(get()) }

    // Сюда мы позже добавим Retrofit и ViewModels, когда их создадим!
}
