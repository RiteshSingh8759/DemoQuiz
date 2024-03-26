package com.example.ktquizz.firebaseRealtimeDb.repository

import com.example.ktquizz.module.RealtimeModel
import com.example.ktquizz.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface RealtimeRepository
{
    fun insert(
        user: RealtimeModel.RealtimeUsers
    ):Flow<ResultState<String>>
    fun getAll():Flow<ResultState<List<RealtimeModel>>>
    fun deleteAll():Flow<ResultState<String>>
    fun delete(key:String):Flow<ResultState<String>>
    fun get(key:String):Flow<ResultState<RealtimeModel>>
    fun update(res: RealtimeModel):Flow<ResultState<String>>
//    fun getQuizResults():Flow<ResultState<List<RealtimeModel>>>
//    fun deleteQuizResults(key:String):Flow<ResultState<Unit>>
//    fun deleteQuestion(key:String):Flow<ResultState<Unit>>
//    fun deleteQuiz(key:String):Flow<ResultState<Unit>>
//    fun getQuestion(key:String):Flow<ResultState<RealtimeModel>>
//    fun updateQuestion(key:String,user:RealtimeModel.RealtimeUsers):Flow<ResultState<Unit>>
//    fun getAnswer(key:String):Flow<ResultState<RealtimeModel>>
//    fun updateAnswer(key:String,user:RealtimeModel.RealtimeUsers):Flow<ResultState<Unit>>
//    fun getContributeAnswer(key:String):Flow<ResultState<RealtimeModel>>
//    fun updateContributeAnswer(key:String,user:RealtimeModel.RealtimeUsers):Flow<ResultState<Unit>>
//    fun getContribute(key:String):Flow<ResultState<RealtimeModel>>
//    fun updateContribute(key:String,user:RealtimeModel.RealtimeUsers):Flow<ResultState<Unit>>
//    fun getResult(key:String):Flow<ResultState<RealtimeModel>>
//    fun updateResult(key:String,user:RealtimeModel.RealtimeUsers):Flow<ResultState<Unit>>
//    fun getQuiz(key:String):Flow<ResultState<RealtimeModel>>
//    fun updateQuiz(key:String,user:RealtimeModel.RealtimeUsers):Flow<ResultState<Unit>>
//    fun getQuizResult(key:String):Flow<ResultState<RealtimeModel>>
//    fun updateQuizResult(key:String,user:RealtimeModel.RealtimeUsers):Flow<ResultState<Unit>>
//
}