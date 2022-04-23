package com.beko.cryptocurrencytrackerapp.ui.homepage.mycoins

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beko.cryptocurrencytrackerapp.R
import com.beko.cryptocurrencytrackerapp.data.model.CoinDetailModel
import com.beko.cryptocurrencytrackerapp.databinding.FavCoinListItemBinding

class MyCoinsAdapter() : ListAdapter<CoinDetailModel, MyCoinsAdapter.CustomViewHolder>(customCallBack){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.fav_coin_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CustomViewHolder(private val binding : FavCoinListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coinDetailModel: CoinDetailModel){
            with(binding){
                itemCoin = coinDetailModel
            }
        }
    }

    companion object{
        val customCallBack = object : DiffUtil.ItemCallback<CoinDetailModel>(){
            override fun areItemsTheSame(
                oldItem: CoinDetailModel,
                newItem: CoinDetailModel
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: CoinDetailModel,
                newItem: CoinDetailModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }


}