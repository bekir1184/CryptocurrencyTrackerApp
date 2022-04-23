package com.beko.cryptocurrencytrackerapp.ui.homepage.mycoins

import com.beko.cryptocurrencytrackerapp.data.model.CoinDetailModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import javax.inject.Inject

class MyCoinsRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    fun getFavCoins() : MutableList<CoinDetailModel>{
        return getFavCoinsFromFirestore()
    }
    private fun getFavCoinsFromFirestore() : MutableList<CoinDetailModel>{
        val blockingQueue : BlockingQueue<MutableList<CoinDetailModel>> = ArrayBlockingQueue(1)
        firestore.collection(firebaseAuth.currentUser!!.uid)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    val favoriteCoinList = mutableListOf<CoinDetailModel>()
                    it.result?.forEach {
                        favoriteCoinList.add(it.toObject(CoinDetailModel::class.java))
                    }
                    blockingQueue.add(favoriteCoinList)
                }
            }
        return blockingQueue.take()
    }
}