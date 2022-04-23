package com.beko.cryptocurrencytrackerapp.ui.homepage.mycoins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beko.cryptocurrencytrackerapp.data.model.CoinDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCoinsViewModel @Inject constructor(
    private val myCoinsRepository: MyCoinsRepository
) : ViewModel() {
    private val _favCoins = MutableLiveData<MutableList<CoinDetailModel>>()
    val favCoins : LiveData<MutableList<CoinDetailModel>>
        get() = _favCoins

    fun getFavCoins(){
        CoroutineScope(Dispatchers.IO).launch {
            _favCoins.postValue(myCoinsRepository.getFavCoins())
        }
    }
}