package com.beko.cryptocurrencytrackerapp.ui.homepage.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.beko.cryptocurrencytrackerapp.R
import com.beko.cryptocurrencytrackerapp.data.model.CoinModel
import com.beko.cryptocurrencytrackerapp.databinding.CoinListItemBinding

class HomeAdapter () : ListAdapter<CoinModel,HomeAdapter.CustomViewHolder>(customCallBack) {

    private var onItemCoinClickListener: ((coinId: String) -> Unit)? = null

    fun setOnItemCoinClickListener(onItemCoinClickListener: ((coinId: String) -> Unit)?) {
        this.onItemCoinClickListener = onItemCoinClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.coin_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class CustomViewHolder(private val binding: CoinListItemBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                onItemCoinClickListener?.invoke(
                    getItem(adapterPosition).id!!
                )
            }
        }
        fun bind(coinModel: CoinModel) {
            with(binding) {
                itemCoin = coinModel
            }
        }
    }

    companion object {
        val customCallBack = object : DiffUtil.ItemCallback<CoinModel>() {
            override fun areItemsTheSame(oldItem: CoinModel, newItem: CoinModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CoinModel, newItem: CoinModel): Boolean {
                return oldItem == newItem
            }
        }
    }


}