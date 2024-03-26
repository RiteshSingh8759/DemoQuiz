package com.example.ktquizz.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

fun Context.showMsg(
    msg: String,
    duration: Int = Toast.LENGTH_SHORT
)= Handler(Looper.getMainLooper()).post {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}