package com.example.mosque.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mosque.model.ErrorModel
import com.example.mosque.model.respons.ApiRespons
import com.example.mosque.network.Services
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RegisterViewModel : ViewModel() {

    private val disposable =  CompositeDisposable()
    val registerData = MutableLiveData<ApiRespons.RegisterRespons>()
    val errorData = MutableLiveData<ApiRespons.RegisterRespons>()
    val registerLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()



    fun submitRegister(email: String, username: String, fullName: String, password: String) {

        disposable.add(
            Services.getHomeMosque().register(fullName,username,email,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ registerRespons ->

                if (registerRespons.code == 200){
                    registerData.value = registerRespons
                    registerLoadError.value = false

                } else {
                    registerLoadError.value = true
                    errorData.value = registerRespons
                }



            },{ err->
                registerLoadError.value = true
                loading.value = false
            }))
    }
}