package com.beko.cryptocurrencytrackerapp.utils

import android.text.TextUtils
import android.util.Patterns

object Constants {

    const val BASE_URL= "https://api.coingecko.com/api/v3/"

    fun String.isValidEmail() =
        TextUtils.isEmpty(this).not() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun String.isValidPassword() =
        TextUtils.isEmpty(this).not() && this.length >= 6





}