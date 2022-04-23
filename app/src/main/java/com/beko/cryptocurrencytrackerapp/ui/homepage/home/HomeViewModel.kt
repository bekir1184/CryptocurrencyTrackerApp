package com.beko.cryptocurrencytrackerapp.ui.homepage.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beko.cryptocurrencytrackerapp.data.model.CoinModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel(){
    private var _coins = MutableLiveData<MutableList<CoinModel>>()
    val coins : LiveData<MutableList<CoinModel>>
        get() = _coins

    fun getCoinsData(){
        CoroutineScope(Dispatchers.IO).launch {
            _coins.postValue(homeRepository.getCoinList())
        }
    }
}