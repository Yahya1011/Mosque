package com.example.mosque.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mosque.model.LoginModel
import com.example.mosque.model.respons.ApiRespons
import com.example.mosque.network.Services
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel : ViewModel() {
    private val disposable =  CompositeDisposable()
    val loginData = MutableLiveData<ApiRespons.LoginRespons>()
    val loginLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()



    fun submitLogin(email: String, password: String) {
        disposable.add(
            Services.getHomeMosque().login(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ loginRespons ->

                    if (loginRespons.code !=200){
                        loginLoadError.value = true
                        loginData.value = loginRespons
                    } else {
                        loginData.value = loginRespons
                        loginLoadError.value = false
                    }



                },{ err->
                    loginLoadError.value = true
                    loading.value = false
                }))


    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}