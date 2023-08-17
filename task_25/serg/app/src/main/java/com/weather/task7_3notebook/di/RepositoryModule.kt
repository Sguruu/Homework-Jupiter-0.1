package com.weather.task7_3notebook.di

import com.weather.task7_3notebook.data.repository.BaseRepositoryImpl
import com.weather.task7_3notebook.data.repository.CityRepositoryImpl
import com.weather.task7_3notebook.data.repository.ContactRepositoryImpl
import com.weather.task7_3notebook.data.repository.SearchRepositoryImpl
import com.weather.task7_3notebook.data.repository.WeatherRepositoryImpl
import com.weather.task7_3notebook.domain.repository.BaseRepository
import com.weather.task7_3notebook.domain.repository.CityRepository
import com.weather.task7_3notebook.domain.repository.ContactRepository
import com.weather.task7_3notebook.domain.repository.SearchRepository
import com.weather.task7_3notebook.domain.repository.WeatherRepository
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
