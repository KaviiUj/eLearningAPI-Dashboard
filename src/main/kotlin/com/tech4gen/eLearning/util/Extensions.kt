package com.tech4gen.eLearning.util

import com.google.gson.Gson

fun Any.toJson(): String {
    return Gson().toJson(this)
}