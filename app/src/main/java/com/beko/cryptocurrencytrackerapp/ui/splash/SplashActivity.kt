package com.beko.cryptocurrencytrackerapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.beko.cryptocurrencytrackerapp.R
import com.beko.cryptocurrencytrackerapp.ui.homepage.HomepageActivity
import com.beko.cryptocurrencytrackerapp.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val splashViewModel : SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            if (splashViewModel.userLogin()) startActivity(Intent(this, HomepageActivity::class.java))
            else startActivity(Intent(this, RegisterActivity::class.java))
        }, 2000)



    }
}
