package com.weather.task7_3notebook.di

import com.weather.task7_3notebook.repository.BaseRepository
import com.weather.task7_3notebook.repository.BaseRepositoryImpl
import com.weather.task7_3notebook.repository.CityRepository
import com.weather.task7_3notebook.repository.CityRepositoryImpl
import com.weather.task7_3notebook.repository.ContactRepository
import com.weather.task7_3notebook.repository.ContactRepositoryImpl
import com.weather.task7_3notebook.repository.SearchRepository
import com.weather.task7_3notebook.repository.SearchRepositoryImpl
import com.weather.task7_3notebook.repository.WeatherRepository
import com.weather.task7_3notebook.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    // можем вместо Provides использовать Binds в абстрактном классе, тогда dagger сам реализует
    // абстракную функцию
    @Binds
    abstract fun providesBaseRepository(impl: BaseRepositoryImpl): BaseRepository

    @Binds
    abstract fun providesCityRepository(impl: CityRepositoryImpl): CityRepository

    @Binds
    abstract fun providesContactRepository(impl: ContactRepositoryImpl): ContactRepository

    @Binds
    abstract fun providesSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @Binds
    abstract fun providesWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository
}
