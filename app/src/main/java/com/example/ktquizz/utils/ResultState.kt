package com.example.ktquizz.utils

import com.example.ktquizz.R

sealed class ResultState<out R>
{
    data class Success<out R> (val data: R) : ResultState<R>()
    data class Failure(val msg: Throwable) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()

}