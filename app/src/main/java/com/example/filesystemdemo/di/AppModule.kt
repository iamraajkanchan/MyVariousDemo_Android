package com.example.filesystemdemo.di

import android.content.Context
import android.graphics.Paint
import android.location.LocationManager
import com.example.filesystemdemo.utilities.DataHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.WebSocket
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

    @Provides
    @ViewScoped
    fun providePaint() = Paint()

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient()

}