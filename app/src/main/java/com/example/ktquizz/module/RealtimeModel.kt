package com.example.ktquizz.module

data class RealtimeModel(
    val user: RealtimeUsers?,
    val key:String?=""
//    val quiz:RealtimeQuiz?,
//    val result:RealtimeResult?,
//    val contribute:RealtimeContribute?,
//    val question:RealtimeQuestion?  ,
//    val answer:RealtimeAnswer?,
//    val contributeAnswer:RealtimeContributeAnswer?,


)
{
    data class RealtimeUsers(
        val email:String?="",
        val password:String?="",
        val name:String?="",
        val coins:Int=0
    )
}
