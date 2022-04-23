package com.beko.cryptocurrencytrackerapp.ui.homepage.mycoins

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.beko.cryptocurrencytrackerapp.R
import com.beko.cryptocurrencytrackerapp.databinding.FragmentMyCoinsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCoinsFragment : Fragment() {
    private val myCoinsViewModel : MyCoinsViewModel by viewModels()
    private lateinit var binding : FragmentMyCoinsBinding
    var myCoinsAdapter  = MyCoinsAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyCoinsBinding.inflate(inflater,container,false)
        binding.favRecyclerView.adapter = myCoinsAdapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        setRecyclerView()
        super.onActivityCreated(savedInstanceState)
        myCoinsViewModel.favCoins.value.also {
            myCoinsViewModel.getFavCoins().also { setObserver() }
        }
    }

    @SuppressLint("WrongConstant")
    private fun setRecyclerView() {
        binding.favRecyclerView.layoutManager = LinearLayoutManager(context,
        LinearLayout.VERTICAL,false)

        binding.favRecyclerView.apply {
            adapter = myCoinsAdapter
        }

    }

    private fun setObserver() {
        myCoinsViewModel.favCoins.observe(this.viewLifecycleOwner){ coins ->
            myCoinsAdapter.submitList(coins)
        }
    }
}