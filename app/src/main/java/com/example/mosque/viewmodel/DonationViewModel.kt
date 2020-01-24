package com.example.mosque.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mosque.model.Mosque
import com.example.mosque.network.Services
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DonationViewModel  :  ViewModel(){

    private val disposable = CompositeDisposable()
    val laporanLoadError = MutableLiveData<Boolean>()
    val mosquesData = MutableLiveData<Mosque>()
    val masjidLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()


    fun refresh(id:Int){
        fetchMasjid(id)
    }

    private fun fetchMasjid(id:Int) {
        loading.value = true
        println("data = $id")
        disposable.add(
            Services.getHomeMosque().getDetailMosque(id.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ mosqueRespons ->
                    println("MOSQUE RESP ${mosqueRespons}")

                    mosquesData.value = mosqueRespons
                    masjidLoadError.value = false
                    loading.value = false
//                    mosquesData.value = mosqueRespons
//                    masjidLoadError.value = false
//                    loading.value = false
                },{ err->
                    masjidLoadError.value = true
                    loading.value = false
                }))
    }
}