package com.beko.cryptocurrencytrackerapp.ui.splash

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.beko.cryptocurrencytrackerapp.ui.homepage.HomepageActivity
import com.beko.cryptocurrencytrackerapp.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    fun userLogin() : Boolean {
        val currentUser =firebaseAuth.currentUser
        return currentUser != null
    }
}