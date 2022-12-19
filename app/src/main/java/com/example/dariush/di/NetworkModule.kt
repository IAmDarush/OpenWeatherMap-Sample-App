package com.example.dariush.di

import com.example.dariush.data.remote.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Provides
  @Singleton
  fun providesWeatherRepository(): WeatherRepository {
    TODO("unimplemented")
  }

}