package com.weather.task7_3notebook.di

import android.app.Application
import androidx.room.Room
import com.weather.task7_3notebook.base.db.dao.CityDao
import com.weather.task7_3notebook.base.db.dao.ContactDao
import com.weather.task7_3notebook.base.db.dao.ContactWithCityDao
import com.weather.task7_3notebook.base.db.database.ContactDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
// указание к какому компоненту пренадлежит данный модуль
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(context: Application): ContactDataBase {
        // scoped, будет создан только дин раз в течение жизни приложения
        // инстанс переиспользуется
        return Room.databaseBuilder(
            context,
            ContactDataBase::class.java,
            ContactDataBase.DB_NAME
        ).build()
    }

    @Provides
    fun provideCityDao(db: ContactDataBase): CityDao {
        // нет scope, новый инстанс будет создан каждый раз при запросе CityDao
        return db.CityDao()
    }

    @Provides
    fun provideContactDao(db: ContactDataBase): ContactDao {
        return db.ContactDao()
    }

    @Provides
    fun provideContactWithCityDao(db: ContactDataBase): ContactWithCityDao {
        return db.ContactWithCityDao()
    }
}
