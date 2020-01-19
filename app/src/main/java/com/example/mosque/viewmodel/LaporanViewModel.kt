package com.example.mosque.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mosque.model.LaporanModel
import com.example.mosque.model.Mosque
import com.example.mosque.network.Services
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LaporanViewModel : ViewModel() {

    private val disposable = CompositeDisposable()
    val laporan = MutableLiveData<List<LaporanModel>>()
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

    fun refreshLaporan(){
        fetchLaporan()
    }

    private fun fetchLaporan() {
        val mockData = listOf(
            LaporanModel("2020-01-19","Infaq Hamba Allah",0,1000000),
            LaporanModel("2020-01-20","Beli Semen",200000,0),
            LaporanModel("2020-01-19","Infaq Udin",0,10000000),
            LaporanModel("2020-01-20","Beli Semen",12000000,0),
            LaporanModel("2020-01-19","Infaq Asep",0,100000000),
            LaporanModel("2020-01-20","Beli Sapu",200000,0)
        )

        laporanLoadError.value = false
        loading.value = false
        laporan.value = mockData
    }
}