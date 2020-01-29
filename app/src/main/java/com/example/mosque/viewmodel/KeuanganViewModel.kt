package com.example.mosque.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mosque.model.LaporanModel
import com.example.mosque.network.Services
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class KeuanganViewModel :  ViewModel(){

    private val disposable = CompositeDisposable()
    val keuanganLoadError = MutableLiveData<Boolean>()
    val keuanganData = MutableLiveData<List<LaporanModel>>()
    val loading = MutableLiveData<Boolean>()

    fun refreshKeuangan(mosqueId:Int){
        fetchKeuangan(mosqueId)
    }

    private fun fetchKeuangan(id: Int) {
        disposable.add(
            Services.getLaporanDetail().getDetailLaporan(id.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ keuanganRespons ->
                    println("Keuangan RESP ${keuanganRespons.data.size}")

                    keuanganData.value = keuanganRespons.data
                    keuanganLoadError.value = false
                    loading.value = false
                },{ err->
                    keuanganLoadError.value = true
                    loading.value = false
                }))
    }

}