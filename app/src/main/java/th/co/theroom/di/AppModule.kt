package th.co.theroom.di

import androidx.room.Room
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import th.co.theroom.booking.BookingFragmentRepositoryImpl
import th.co.theroom.booking.BookingFragmentViewModel
import th.co.theroom.bookingdata.BookingDataFragmentRepositoryImpl
import th.co.theroom.bookingdata.BookingDataFragmentViewModel
import th.co.theroom.bookingedit.BookingEditFragmentRepositoryImpl
import th.co.theroom.bookingedit.BookingEditFragmentViewModel
import th.co.theroom.room.TheRoomDatabase
import th.co.theroom.shareviewmodel.ShareViewModel
import th.co.theroom.splashscreen.SplashScreenViewModel
import th.co.theroom.splashscreen.SplashScreenRepositoryImpl
import th.co.theroom.usecase.*

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), TheRoomDatabase::class.java, TheRoomDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}

val repositoryModule = module {
    single {
        val database: TheRoomDatabase = get()
        SplashScreenRepositoryImpl(database.theRoomDao())
    }
    single {
        val database: TheRoomDatabase = get()
        BookingFragmentRepositoryImpl(database.theRoomDao())
    }
    single {
        val database: TheRoomDatabase = get()
        BookingDataFragmentRepositoryImpl(database.theRoomDao())
    }
    single {
        val database: TheRoomDatabase = get()
        BookingEditFragmentRepositoryImpl(database.theRoomDao())
    }
}

val useCaseModule = module {
    factory { InsertRoomUserCase(get()) }
    factory { SelectRoomByBedTypeUseCase(get()) }
    factory { SelectRoomByPeopleSizeUseCase(get()) }
    factory { InsertBookingRoomUserCase(get()) }
    factory { UpdateStatusRoomUserCase(get()) }
    factory { SelectBookingListUseCase(get()) }
    factory { CancelBookingUserCase(get()) }
    factory { CheckInUserCase(get()) }
    factory { SelectRoomByBedTypeAllUseCase(get()) }
    factory { SelectRoomByPeopleSizeAllUseCase(get()) }
    factory { EditBookingRoomUserCase(get()) }
}

val viewModelModule = module {
    viewModel { SplashScreenViewModel(get()) }
    viewModel { BookingFragmentViewModel(get(), get(), get(), get()) }
    viewModel { ShareViewModel() }
    viewModel { BookingDataFragmentViewModel(get(), get(), get(), get()) }
    viewModel { BookingEditFragmentViewModel(get(), get(), get(), get()) }
}