package com.beko.cryptocurrencytrackerapp.ui.detailpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beko.cryptocurrencytrackerapp.data.model.CoinDetailModel
import com.squareup.okhttp.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPageViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel(){
    private var _coin =MutableLiveData<CoinDetailModel>()
    val coin : LiveData<CoinDetailModel>
        get() = _coin
    private var _isAdded = MutableLiveData<Boolean>()
    val isAdded : LiveData<Boolean>
        get() = _isAdded


    fun addFavorite(coin : CoinDetailModel){
        CoroutineScope(Dispatchers.IO).launch {
            _isAdded.postValue(detailRepository.addFavorite(coin))
        }
    }
    fun getCoinData(id : String){
        CoroutineScope(Dispatchers.IO).launch {
            _coin.postValue(detailRepository.getCoinDetailPage(id))
        }
    }
}