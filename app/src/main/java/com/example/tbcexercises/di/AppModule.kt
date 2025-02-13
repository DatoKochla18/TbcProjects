package com.example.tbcexercises.di


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.tbcexercises.data.local.dataStore.UserManager
import com.example.tbcexercises.data.local.room.AppDatabase
import com.example.tbcexercises.data.local.room.daos.RemoteKeysDao
import com.example.tbcexercises.data.local.room.daos.UserDao
import com.example.tbcexercises.data.remote.apis.AuthApi
import com.example.tbcexercises.data.remote.apis.UserApi
import com.example.tbcexercises.data.repository.AuthRepositoryImpl
import com.example.tbcexercises.data.repository.UserRepositoryImpl
import com.example.tbcexercises.domain.repository.AuthRepository
import com.example.tbcexercises.domain.repository.UserRepository
import com.example.tbcexercises.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        return retrofit
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    //Repositories
    @Provides
    fun provideUserRepository(userApi: UserApi, appDatabase: AppDatabase): UserRepository {
        return UserRepositoryImpl(userApi = userApi, database = appDatabase)
    }

    @Provides
    fun provideAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authApi = authApi)
    }


    //Room
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "App.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.usersDao()
    }

    @Provides
    @Singleton
    fun provideRemoteKeysDao(database: AppDatabase): RemoteKeysDao {
        return database.remoteKeysDao()
    }

    //datastore
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("user-session") }
        )
    }

    @Provides
    @Singleton
    fun provideUserManager(dataStore: DataStore<Preferences>): UserManager {
        return UserManager(dataStore)
    }

}