package com.beko.cryptocurrencytrackerapp.ui.detailpage

import android.util.Log
import com.beko.cryptocurrencytrackerapp.api.RetrofitClient
import com.beko.cryptocurrencytrackerapp.data.model.CoinDetailModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val firebaseFireStore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
){
    fun getCoinDetailPage(id : String) : CoinDetailModel{
        return getDetailFromApi(id).take()
    }
    fun addFavorite(coin: CoinDetailModel) : Boolean = addFavoritetoFirestore(coin)

    private fun addFavoritetoFirestore(coin : CoinDetailModel) : Boolean{
        val blockingQueue : BlockingQueue<Boolean> = ArrayBlockingQueue(1)
        firebaseFireStore
            .collection(firebaseAuth.currentUser!!.uid)
            .add(coin)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    blockingQueue.add(true)
                }else{
                    blockingQueue.add(false)
                }
            }
        return blockingQueue.take()
    }

    private fun getDetailFromApi(id : String):BlockingQueue<CoinDetailModel>{
        val blockingQueue : BlockingQueue<CoinDetailModel> = ArrayBlockingQueue(1)
        RetrofitClient.retrofitClient.getCoinbyId(id).enqueue(object : Callback<CoinDetailModel> {
            override fun onResponse(
                call: Call<CoinDetailModel>,
                response: Response<CoinDetailModel>
            ) {
                if(response.isSuccessful){
                    blockingQueue.add(response.body())
                }else{
                    Log.d(TAG,response.message())
                }
            }
            override fun onFailure(call: Call<CoinDetailModel>, t: Throwable) {
                Log.d(TAG,t.message.toString())
            }

        })

        return blockingQueue
    }


    companion object {
        private const val TAG = "Detail ViewModel "
    }
}