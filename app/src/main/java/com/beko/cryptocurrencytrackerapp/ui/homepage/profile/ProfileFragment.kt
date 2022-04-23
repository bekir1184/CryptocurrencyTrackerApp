package com.beko.cryptocurrencytrackerapp.ui.homepage.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beko.cryptocurrencytrackerapp.R
import com.beko.cryptocurrencytrackerapp.databinding.FragmentProfileBinding
import com.beko.cryptocurrencytrackerapp.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        setEmail()
        setOnClicks()
        return binding.root
    }

    private fun setOnClicks() {
        binding.logOutBtn.setOnClickListener {
            logOut()
        }
    }

    private fun setEmail() {
        val user = FirebaseAuth.getInstance().currentUser
        binding.email.text = user!!.email.toString()
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this.requireContext(), RegisterActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

}