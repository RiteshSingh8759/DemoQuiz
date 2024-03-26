package com.example.ktquizz.di

import com.example.ktquizz.firebaseRealtimeDb.repository.RealtimeDbRepository
import com.example.ktquizz.firebaseRealtimeDb.repository.RealtimeRepository
import com.example.ktquizz.firebaseauth.repository.AuthRepository
import com.example.ktquizz.firebaseauth.repository.AuthRepositoryImpl
import com.example.ktquizz.firestoredb.repository.FirestoreDbRepositoryImpl
import com.example.ktquizz.firestoredb.repository.FirestoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule
{
    @Binds
    abstract fun providesRealtimeRepository(
        repo:RealtimeDbRepository
    ):RealtimeRepository
    @Binds
    abstract fun providesFirestoreRepository(
        repo: FirestoreDbRepositoryImpl
    ):FirestoreRepository
    @Binds
    abstract fun providesFirebaseAuthRepository(
        repo: AuthRepositoryImpl
    ): AuthRepository

}