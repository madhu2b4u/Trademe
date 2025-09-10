package com.org.trademe.core.network

import com.org.trademe.core.network.qualifiers.IO
import com.org.trademe.core.network.qualifiers.MainThread
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

private const val TRADE_ME_BASE_URL = "https://api.tmsandbox.co.nz/v1/"
private const val OAUTH_CONSUMER_KEY = "A1AC63F0332A131A78FAC304D007E7D1"
private const val OAUTH_SIGNATURE = "EC7F18B17A062962C6930A8AE88B16C7&"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .addHeader(
                        "Authorization",
                        "OAuth oauth_consumer_key=$OAUTH_CONSUMER_KEY,oauth_signature_method=PLAINTEXT,oauth_signature=$OAUTH_SIGNATURE"
                    )

                chain.proceed(requestBuilder.build())
            }
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(TRADE_ME_BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @IO
    @Provides
    fun providesIoDispatcher(): CoroutineContext = Dispatchers.IO

    @MainThread
    @Provides
    fun providesMainThreadDispatcher(): CoroutineContext = Dispatchers.Main
}