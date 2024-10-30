package com.ahmed.baimstask.di

import android.content.Context
import androidx.room.Room
import com.ahmed.baimstask.data.local.AppDatabase
import com.ahmed.baimstask.data.local.CityDao
import com.ahmed.baimstask.data.local.WeatherDao
import com.ahmed.baimstask.data.remote.ApiService
import com.ahmed.baimstask.data.repository.WeatherRepositoryImpl
import com.ahmed.baimstask.domain.repositry.WeatherRepository
import com.ahmed.baimstask.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "baims_db"
        ).build()
    }

    @Provides
    fun provideCityDao(database: AppDatabase): CityDao {
        return database.cityDao()
    }

    @Provides
    fun provideWeatherDao(database: AppDatabase): WeatherDao {
        return database.weatherDao()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        apiService: ApiService,
        cityDao: CityDao,
        weatherDao: WeatherDao
    ): WeatherRepository {
        return WeatherRepositoryImpl(apiService, cityDao, weatherDao)
    }

}
