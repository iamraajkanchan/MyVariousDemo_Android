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
import com.example.filesystemdemo.retrofit.RetrofitInterceptorForJSONPlaceHolder
import com.example.filesystemdemo.retrofit.RetrofitInterceptorForUnsplash
import com.example.filesystemdemo.retrofit.RetrofitInterceptorWithToken
import com.example.filesystemdemo.retrofit.UnsplashService
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
    fun provideRetrofitInterceptorForJSONPlaceHolder(): RetrofitInterceptorForJSONPlaceHolder =
        RetrofitInterceptorForJSONPlaceHolder()

    @Provides
    @Singleton
    @RetrofitForJsonPlaceholder
    fun provideRetrofitForJSONPlaceHolder(retrofitInterceptorForJSONPlaceHolder: RetrofitInterceptorForJSONPlaceHolder) =
        RetrofitHelper.getRetrofitInstanceForJSONPlaceHolder(retrofitInterceptorForJSONPlaceHolder)

    @Provides
    @Singleton
    fun provideApiServiceWithoutToken(@RetrofitForJsonPlaceholder retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofitInterceptorForUnsplash(): RetrofitInterceptorForUnsplash =
        RetrofitInterceptorForUnsplash()

    @Provides
    @Singleton
    @RetrofitForUnsplash
    fun provideRetrofitForUnsplash(retrofitInterceptorForUnsplash: RetrofitInterceptorForUnsplash) =
        RetrofitHelper.getRetrofitInstanceForUnsplash(retrofitInterceptorForUnsplash)

    @Provides
    @Singleton
    fun provideUnsplashService(@RetrofitForUnsplash retrofit: Retrofit): UnsplashService =
        retrofit.create(UnsplashService::class.java)

    @Provides
    @Singleton
    fun provideAppRepositoryWithoutToken(
        apiService: ApiService,
        unsplashService: UnsplashService
    ): AppRepository =
        AppRepository(apiService, unsplashService)


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
        RetrofitHelper.getRetrofitInstanceForJSONPlaceHolder(interceptorWithToken)

    fun provideApiServiceWithToken(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    fun provideAppRepositoryWithToken(
        apiService: ApiService,
        unsplashService: UnsplashService
    ): AppRepository =
        AppRepository(apiService, unsplashService)

    @Provides
    @Singleton
    fun provideNetworkManager(@ApplicationContext context: Context) = NetworkManager(context)

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitForJsonPlaceholder

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitForUnsplash
