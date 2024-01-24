package com.example.filesystemdemo.di

import android.content.Context
import android.location.LocationManager
import com.example.filesystemdemo.utilities.DataHelper
import com.example.filesystemdemo.utilities.Utility
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesDataHelper(@ApplicationContext context: Context) = DataHelper(context)

    @Provides
    @Singleton
    fun providesLocationManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
}