package com.beko.cryptocurrencytrackerapp.api

import com.beko.cryptocurrencytrackerapp.utils.Constants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

abstract class RetrofitClient {
    companion object {
        private val gson = GsonBuilder()
            .setLenient()
            .create()
        private val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val retrofitClient: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }
}