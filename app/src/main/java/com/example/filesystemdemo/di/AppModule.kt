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
import com.example.filesystemdemo.utilities.NetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Qualifier
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
    @ActivityScoped
    fun providePaint() = Paint()

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient()

    private const val DATABASE_NAME = "AppDatabase"

    private const val DATABASE_VERSION = 1

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppSQLDatabase = AppSQLDatabase(
        AlbumTable.QUERY_STRING, context, DATABASE_NAME, DATABASE_VERSION
    )

    @Provides
    @Singleton
    fun provideAppDatabaseImpl(database: AppSQLDatabase): AppSQLDatabaseImpl =
        AppSQLDatabaseImpl(database)

    @Provides
    @Singleton
    fun provideRetrofitInterceptor(): RetrofitInterceptor = RetrofitInterceptor()

    @Provides
    @Singleton
    fun provideRetrofit(retrofitInterceptor: RetrofitInterceptor) =
        RetrofitHelper.getRetrofitInstance(retrofitInterceptor)

    @Provides
    @Singleton
    fun provideApiServiceWithoutToken(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideAppRepositoryWithoutToken(apiService: ApiService): AppRepository =
        AppRepository(apiService)


    const val PREFERENCE_NAME = "AppSharedPreference"
    const val PREFERENCE_TOKEN = "Application Token"

    @Provides
    @Singleton
    fun provideRetrofitInterceptorWithToken(@ApplicationContext context: Context): RetrofitInterceptorWithToken {
        val sharedPreference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val token = sharedPreference.getString(PREFERENCE_TOKEN, "") ?: ""
        return if (token != "") {
            RetrofitInterceptorWithToken(token)
        } else {
            throw Exception("Token Not Found")
        }
    }

    fun provideRetrofitForToken(interceptorWithToken: RetrofitInterceptorWithToken) =
        RetrofitHelper.getRetrofitInstance(interceptorWithToken)

    fun provideApiServiceWithToken(@RETROFIT_WITH_TOKEN retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    fun provideAppRepositoryWithToken(@API_WITH_TOKEN apiService: ApiService): AppRepository =
        AppRepository(apiService)

    @Provides
    @Singleton
    fun provideNetworkManager(@ApplicationContext context: Context) = NetworkManager(context)

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RETROFIT_WITH_TOKEN

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RETROFIT_WITHOUT_TOKEN

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class API_WITH_TOKEN

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class API_WITHOUT_TOKEN

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class REPOSITORY_WITH_TOKEN

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class REPOSITORY_WITHOUT_TOKEN
