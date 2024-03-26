package com.example.ktquizz.firestoredb.repository

import com.example.ktquizz.firestoredb.module.FirestoreModel
import com.example.ktquizz.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {

    fun insert(
        user: FirestoreModel.FirestoreUser
    ) : Flow<ResultState<String>>

    fun getUsers() : Flow<ResultState<List<FirestoreModel>>>

    fun delete(key:String) : Flow<ResultState<String>>

    fun update(
        user:FirestoreModel
    ) : Flow<ResultState<String>>

}