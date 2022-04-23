package com.beko.cryptocurrencytrackerapp.ui.register.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beko.cryptocurrencytrackerapp.utils.Constants.isValidEmail
import com.beko.cryptocurrencytrackerapp.utils.Constants.isValidPassword
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel(){
    private val _isDone = MutableLiveData<Boolean>()
    val isDone: LiveData<Boolean>
        get() = _isDone

    fun signIn(email: String, password: String){
        if (!(email).isValidEmail()) return
        if ((password).isValidPassword()) {
            signInEmailAndPassword(email,password)
        }
    }
    private fun signInEmailAndPassword(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _isDone.postValue(true)
                } else {
                    _isDone.postValue(false)
                }
            }
        }
    }

}