package com.beko.cryptocurrencytrackerapp.ui.homepage.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beko.cryptocurrencytrackerapp.data.model.CoinModel
import com.beko.cryptocurrencytrackerapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private var wholeCoinList = mutableListOf<CoinModel>()
    private val homeViewModel : HomeViewModel by viewModels()
    private val homeAdapter = HomeAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentHomeBinding.inflate(inflater,container,false)
        binding.homeRecyclerView.adapter = homeAdapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        setOnClicks()
        homeViewModel.coins.value.also {
            if(it.isNullOrEmpty()){
                binding.shimmerLayout.startShimmer()
                homeViewModel.getCoinsData().also { setObserver() }
            }else{
                wholeCoinList.addAll(it)
                homeAdapter.submitList(it).also { stopShimmer() }
                binding.textInputEditText.setText("")
            }
        }
    }

    private fun setOnClicks() {
        homeAdapter.setOnItemCoinClickListener { coinId ->
            val action = HomeDirections.actionHome2ToDetailPage(coinId)
            findNavController().navigate(action)
        }
    }

    private fun stopShimmer() {
        binding.shimmerLayout.stopShimmer()
        binding.shimmerLayout.visibility = View.GONE
        binding.homeRecyclerView.visibility = View.VISIBLE
    }

    @SuppressLint("WrongConstant")
    private fun setupRecyclerView() {
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(context,
            LinearLayout.VERTICAL,false)

        binding.homeRecyclerView.apply {
            adapter = homeAdapter
        }
        binding.textInputEditText.doOnTextChanged { text, _, _, _ ->
            homeAdapter.submitList(wholeCoinList.filter {
                it.name!!.contains(
                    (text ?: ""),
                    true
                )
            })
        }
    }

    private fun setObserver() {
        homeViewModel.coins.observe(this.viewLifecycleOwner) { coinList ->
            homeAdapter.submitList(coinList)
            wholeCoinList.addAll(coinList)
            stopShimmer()
        }
    }


}