package com.example.filesystemdemo.di

import android.content.Context
import android.graphics.Paint
import android.location.LocationManager
import com.example.filesystemdemo.db.AlbumTable
import com.example.filesystemdemo.db.AppSQLDatabase
import com.example.filesystemdemo.db.AppSQLDatabaseImpl
import com.example.filesystemdemo.repository.AppRepository
import com.example.filesystemdemo.retrofit.ApiService
import com.example.filesystemdemo.retrofit.RetrofitHelper
import com.example.filesystemdemo.retrofit.RetrofitInterceptor
import com.example.filesystemdemo.retrofit.RetrofitInterceptorWithToken
import com.example.filesystemdemo.utilities.DataHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
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

    private const val DATABASE_NAME = "AppDatabase"

    private const val DATABASE_VERSION = 1

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) = AppSQLDatabase(
        AlbumTable.QUERY_STRING, context, DATABASE_NAME, DATABASE_VERSION
    )

    @Provides
    @Singleton
    fun provideAppDatabaseImpl(database: AppSQLDatabase) = AppSQLDatabaseImpl(database)

    @Provides
    @Singleton
    fun provideRetrofitInterceptor() = RetrofitInterceptor()

    @Provides
    @Singleton
    fun provideRetrofit(retrofitInterceptor: RetrofitInterceptor) =
        RetrofitHelper.getRetrofitInstance(retrofitInterceptor)

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideAppRepository(apiService: ApiService) : AppRepository = AppRepository(apiService)


    const val PREFERENCE_NAME = "AppSharedPreference"
    const val PREFERENCE_TOKEN = "Application Token"

    @Provides
    @Singleton
    fun provideRetrofitInterceptorWithToken(@ApplicationContext context: Context): RetrofitInterceptorWithToken? {
        val sharedPreference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val token = sharedPreference.getString(PREFERENCE_TOKEN, "") ?: ""
        return if (token != "") {
            RetrofitInterceptorWithToken(token)
        } else {
            null
        }
    }


}