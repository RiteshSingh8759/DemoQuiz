package com.example.ktquizz.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule
{
    @Provides
    @Singleton
    fun provideRealtimeDb():DatabaseReference= Firebase.database.reference.child("users")

    @Singleton
    @Provides
    fun providesFirestoreDb(): FirebaseFirestore = Firebase.firestore

    @Singleton
    @Provides
    fun providesFirebaseAuth(): FirebaseAuth = Firebase.auth
}