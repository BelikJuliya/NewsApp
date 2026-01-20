package com.ybelik.news.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ybelik.data.NewsRepositoryImpl
import com.ybelik.data.local.LocalDataSource
import com.ybelik.data.local.LocalDataSourceImpl
import com.ybelik.data.local.NewsDataBase
import com.ybelik.data.local.entity.NewsDAO
import com.ybelik.data.remote.ApiKeyInterceptor
import com.ybelik.data.remote.NewsApiService
import com.ybelik.data.remote.RemoteDataSource
import com.ybelik.data.remote.RemoteDataSourceImpl
import com.ybelik.domain.repoository.NewsRepository
import com.ybelik.news.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindRepository(impl: NewsRepositoryImpl): NewsRepository

    @Binds
    @Singleton
    fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource

    @Binds
    @Singleton
    fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource

    companion object {

        @Provides
        @Singleton
        fun provideDataBase(
            @ApplicationContext context: Context
        ): NewsDataBase {
            return Room.databaseBuilder(
                context = context,
                klass = NewsDataBase::class.java,
                name = "news.db"
            ).fallbackToDestructiveMigration(dropAllTables = true).build()
        }

        @Provides
        fun providesNotesDao(
            dataBase: NewsDataBase
        ): NewsDAO {
            return dataBase.newsDao()
        }

        @Provides
        @Singleton
        fun providesBaseUrl(): String = "https://newsapi.org/"
// CAT
        @Provides
        @Singleton
        fun provideOkHttpClient(@ApplicationContext appContext: Context) = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(BuildConfig.API_KEY))
            .addInterceptor(ChuckerInterceptor(appContext))
            .build() ///

        @Provides
        @Singleton
        fun provideRetrofit(
            baseUrl: String,
            client: OkHttpClient,
            converterFactory: Converter.Factory
        ): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(converterFactory)
                .client(client)
                .baseUrl(baseUrl)
                .build()
        }

        @Provides
        @Singleton
        fun provideJson() = Json {
            // Если не все поля из Json указаны в DTO, то игнорируме их
            ignoreUnknownKeys = true
            // Если какие - то поля прийдут пустыми, то используем дефолтные значения
            coerceInputValues = true
        }

        @Provides
        @Singleton
        fun provideJConverterFactory(json: Json): Converter.Factory {
            val contentType = "application/json".toMediaType()
            return json.asConverterFactory(contentType)
        }

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit) = retrofit.create<NewsApiService>()
    }
}