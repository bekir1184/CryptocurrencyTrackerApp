package com.beko.cryptocurrencytrackerapp.api

import com.beko.cryptocurrencytrackerapp.api.paths.Coins
import com.beko.cryptocurrencytrackerapp.data.model.CoinDetailModel
import com.beko.cryptocurrencytrackerapp.data.model.CoinModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET(Coins.markets)
    fun getMarkets(
        @Query("vs_currency") currency: String = "usd"
    ): Call<MutableList<CoinModel>>

    @GET(Coins.detailpage)
    fun getCoinbyId(
        @Path("id") cryptoId: String
    ): Call<CoinDetailModel>
}