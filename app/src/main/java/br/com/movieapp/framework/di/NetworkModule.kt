package br.com.movieapp.framework.di

import br.com.movieapp.BuildConfig
import br.com.movieapp.framework.data.remote.MovieService
import br.com.movieapp.framework.data.remote.ParamsInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT = 15L

    @Provides
    fun provideParamsInterceptor(): ParamsInterceptor {
        return ParamsInterceptor()
    }

    @Provides
    fun provideLogginInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE

                }
            )
        }
    }

    @Provides
    fun provideOkHttpClient(
        paramsInterceptor: ParamsInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(paramsInterceptor)
            .addInterceptor(loggingInterceptor)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideMovieService(
        client: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): MovieService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(converterFactory)
            .build()
            .create(MovieService::class.java)
    }
}