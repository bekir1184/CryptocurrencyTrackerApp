package com.beko.cryptocurrencytrackerapp.ui.register.createaccount

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.beko.cryptocurrencytrackerapp.data.model.UserAuthModel
import com.beko.cryptocurrencytrackerapp.databinding.FragmentCreateAccountBinding
import com.beko.cryptocurrencytrackerapp.ui.homepage.HomepageActivity
import com.beko.cryptocurrencytrackerapp.utils.Constants.isValidEmail
import com.beko.cryptocurrencytrackerapp.utils.Constants.isValidPassword
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountFragment : Fragment() {
    private val createAccountViewModel :CreateAccountViewModel by viewModels()
    private lateinit var binding: FragmentCreateAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAccountBinding.inflate(inflater,container,false)
        setOnClicks()
        setupObserver()
        return binding.root
    }

    private fun setupObserver() {
        createAccountViewModel.isDone.observe(
            this.viewLifecycleOwner, Observer { isRegistered: Boolean ->
                binding.progressBarSignUp.visibility = View.INVISIBLE
                if (isRegistered) {
                    startIntent()
                }
            }
        )
    }

    private fun getUserInput() : UserAuthModel {
        val mail = binding.emailTextInput.text.toString()
        val password = binding.passwordTextInput.text.toString()

        return UserAuthModel(mail,password)
    }

    private fun UserAuthModel.isUserValid(): Boolean {
        return if (this.email.isEmpty() || this.password.isEmpty()) {
            binding.emailTextInput.error = "Verilen Alanların Hepsini Doldurmalısınız"
            false
        } else {
            if (!this.email.isValidEmail()) {
                binding.emailTextInput.error = "Geçersiz Mail"
                false
            } else if (!this.password.isValidPassword()) {
                binding.passwordTextInput.error =
                    "Şifre 6 Haneden Fazla Olmalı"
                false
            } else {
                true
            }
        }
    }

    private fun setOnClicks() {
        binding.signUpBtn.setOnClickListener {
            val user = getUserInput()
            if(user.isUserValid()){
                createAccountViewModel.signUp(user.email,user.password)
                binding.progressBarSignUp.visibility = View.VISIBLE
            }


        }
        binding.alreadyHaveAccountText.setOnClickListener {
            val action = CreateAccountFragmentDirections.actionCreateAccountFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }
    private fun startIntent() {
        startActivity(
            Intent(
                this.requireContext(),
                HomepageActivity::class.java
            )
        ).also { this.requireActivity().finish() }
    }


}