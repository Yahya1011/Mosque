package com.example.mosque.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

class KeuanganViewModel :  ViewModel(){

    private val disposable = CompositeDisposable()
    val keuanganLoadError = MutableLiveData<Boolean>()
    /*val keuanganData = MutableLiveData<Keuangan>()*/
    val loading = MutableLiveData<Boolean>()
}