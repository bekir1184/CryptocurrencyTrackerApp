package com.beko.cryptocurrencytrackerapp.ui.homepage.home

import android.util.Log
import com.beko.cryptocurrencytrackerapp.api.RetrofitClient
import com.beko.cryptocurrencytrackerapp.data.model.CoinModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class HomeRepository @Inject constructor() {

     fun getCoinList() : MutableList<CoinModel>{
        return getCoinListFromApi()
    }
    private  fun getCoinListFromApi() : MutableList<CoinModel> {
        val blockingQueue : BlockingQueue<MutableList<CoinModel>> = ArrayBlockingQueue(1)
        try {
            RetrofitClient.retrofitClient.getMarkets().enqueue(object : Callback<MutableList<CoinModel>>{
                override fun onResponse(
                    call: Call<MutableList<CoinModel>>,
                    response: Response<MutableList<CoinModel>>
                ) {
                    if (response.isSuccessful){
                        blockingQueue.add(response.body())
                    }else{
                        Log.e(TAG,response.code().toString())
                        blockingQueue.add(mutableListOf())
                    }

                }
                override fun onFailure(call: Call<MutableList<CoinModel>>, t: Throwable) {
                    Log.e(TAG,t.message.toString())
                    blockingQueue.add(mutableListOf())
                }

            })
        }catch (e : Exception){
            return mutableListOf()
        }

        return blockingQueue.take()
    }
    companion object {
        private const val TAG = "Home Repository"
    }
}