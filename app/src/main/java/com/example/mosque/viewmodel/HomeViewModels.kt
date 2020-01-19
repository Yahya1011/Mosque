package com.example.mosque.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mosque.model.Mosque
import com.example.mosque.network.Services
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModels : ViewModel(){

    private val disposable = CompositeDisposable()
    val mosquesData = MutableLiveData<List<Mosque>>()
    val masjidLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(){
        fetchMasjid()
    }

    private fun fetchMasjid() {
        loading.value = true
        disposable.add(Services.getHomeMosque().getMosque()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ mosqueRespons ->
                println("MOSQUE RESP ${mosqueRespons.size}")
                mosquesData.value = mosqueRespons
                masjidLoadError.value = false
                loading.value = false
            },{ err->
                masjidLoadError.value = true
                loading.value = false
            }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}