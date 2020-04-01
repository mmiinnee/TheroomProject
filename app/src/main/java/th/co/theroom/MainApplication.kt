package th.co.theroom

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import th.co.theroom.di.databaseModule
import th.co.theroom.di.repositoryModule
import th.co.theroom.di.useCaseModule
import th.co.theroom.di.viewModelModule

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(listOf(databaseModule, repositoryModule, useCaseModule, viewModelModule))
        }
    }
}