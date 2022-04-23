package com.beko.cryptocurrencytrackerapp.ui.detailpage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.beko.cryptocurrencytrackerapp.data.model.CoinDetailModel
import com.beko.cryptocurrencytrackerapp.databinding.FragmentDetailPageBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPage : Fragment() {
    private lateinit var binding : FragmentDetailPageBinding
    private val navArgs : DetailPageArgs by navArgs()
    private val detailPageViewModel : DetailPageViewModel by viewModels()
    private var textSeeAll = true
    private lateinit var coinDetailModel : CoinDetailModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View {
        binding = FragmentDetailPageBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToggleButtons()
        setOnClicks()
        detailPageViewModel.coin.value.also {
            binding.detailShimmer.startShimmer()

            detailPageViewModel.getCoinData(navArgs.coinId).also {
                setObserver()
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setOnClicks() {
        binding.changeTextLines.setOnClickListener {
            if(textSeeAll){
                textSeeAll =false
                binding.coinDecsDetail.maxLines = 99
                binding.changeTextLines.text = "daha az göster..."
            }else{
                textSeeAll =true
                binding.coinDecsDetail.maxLines = 5
                binding.changeTextLines.text = "devamını gör..."
            }
        }
        binding.cardView.setOnClickListener {
            detailPageViewModel.addFavorite(coinDetailModel)

        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(coin: CoinDetailModel){
        coinDetailModel = coin
        binding.coinNameDetail.text = coin.name
        binding.coinCurrentPriceDetail.text = "${coin.market_data!!.current_price!!.usd} $"
        binding.coinDecsDetail.text = coin.description!!.en
        binding.coinHashDetail.text = coin.hashing_algorithm
        binding.coinHourChange24Detail.text = "${coin.priceChangePercentage24h} %"
        if (coin.image!!.thumb.isNullOrEmpty().not()) {
            Glide.with(binding.coinImageDetail.context).load(coin.image!!.large).into(binding.coinImageDetail)
        }
    }



    private fun setObserver() {
        detailPageViewModel.coin.observe(this.viewLifecycleOwner){ coin ->
            setData(coin)
            stopShimmer()
            if(binding.coinDecsDetail.length()>239){
                binding.changeTextLines.visibility = View.VISIBLE
                binding.coinDecsDetail.maxLines = 5
            }
        }
        detailPageViewModel.isAdded.observe(this.viewLifecycleOwner){ isAdded ->
            if(isAdded){
                Toast.makeText(requireContext(), "Favorilerine eklendi", Toast.LENGTH_SHORT).show()
                binding.addFavBtnText.text = "Favorilerine Eklendi"
            }else {
                Toast.makeText(requireContext(), "Bir hata oluştu . Tekrar deneyiniz", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setToggleButtons() {
        binding.toggleButtonGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
                when(checkedId){
                    binding.tenSecond.id -> binding.refreshTime.text = "10sn"
                    binding.thirtySecond.id -> binding.refreshTime.text = "30sn"
                    binding.sixtySecond.id -> binding.refreshTime.text = "60sn"
                }
        }
    }
    private fun stopShimmer() {
        binding.detailShimmer.stopShimmer()
        binding.detailShimmer.visibility = View.GONE
        binding.detailLayout.visibility = View.VISIBLE
    }

}