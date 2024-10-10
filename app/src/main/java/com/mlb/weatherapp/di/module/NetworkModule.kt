package com.mlb.weatherapp.di.module

import android.app.Application
import com.mlb.weatherapp.data.ApiService
import com.mlb.weatherapp.data.ApiUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    /**
     * Provides BaseUrl as string
     */
    @Singleton
    @Provides
    fun provideBaseURL(): String {
        return ApiUrl.BASE_URL
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }


    /**
     * Provides LoggingInterceptor for api information
     */
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideCache(application: Application): Cache {
        // Specify the cache size (10 MB in this example) and the cache directory
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cacheDirectory = File(application.cacheDir, "http_cache")
        return Cache(cacheDirectory, cacheSize.toLong())
    }

    /**
     * Provides custom OkkHttp
     */

    @Singleton
    @Provides
    fun provideOkHttpClient(cache: Cache, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache) // Add the cache to OkHttpClient
            .addInterceptor { chain ->
                val request = chain.request()

                // Check if we have a network connection
                val isNetworkAvailable = true

                val finalRequest = if (isNetworkAvailable) {
                    request.newBuilder()
                        .header("Cache-Control", "public, max-age=3600") // Cache for 1 hour
                        .build()
                } else {
                    request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=604800") // Cached for up to a week
                        .build()
                }

                val response = chain.proceed(finalRequest)
                response.newBuilder()
                    .header("Cache-Control", response.cacheControl.toString()) // Pass cache control headers
                    .build()
            }
            .callTimeout(40, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * Provides ApiServices client for Retrofit
     */
    @Singleton
    @Provides
    fun provideRetrofitClient(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    /**
     * Provides Api Service using retrofit
     */
    @Singleton
    @Provides
    fun provideRestApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}