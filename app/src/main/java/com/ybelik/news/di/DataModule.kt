package com.ybelik.news.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.ybelik.data.local.NewsDataBase
import com.ybelik.data.local.entity.NewsDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

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
        fun providesBaseUrl(): String = "https://newsapi.org/v2/"

        @Provides
        @Singleton
        fun provideOkHttpClient(@ApplicationContext appContext: Context): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(appContext))
            .build()

        @Provides
        @Singleton
        fun provideRetrofit(baseUrl: String, client: OkHttpClient): Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(baseUrl)
            .build()
    }
}

// 2c10d3c4175a4d8db13becb8873ba830