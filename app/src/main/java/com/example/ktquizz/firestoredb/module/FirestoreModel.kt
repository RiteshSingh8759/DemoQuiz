package com.example.ktquizz.firestoredb.module

data class FirestoreModel(
    val user: FirestoreUser?,
    val key:String? = ""
 )
{
    data class FirestoreUser(
        val name:String?="",
        val email:String?="",
        val password:String?="",
        val coins:Int?=0
    )
}