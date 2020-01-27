package com.example.mosque.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mosque.helper.AppPreferencesHelper
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
        disposable.add(
            Services.getHomeMosque().getDetailMosque(id.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ mosqueRespons ->
                    println("DONATION RESP $mosqueRespons")
                    mosquesData.value = mosqueRespons
                    masjidLoadError.value = false
                    loading.value = false
                },{ err->
                    masjidLoadError.value = true
                    loading.value = false
                }))
    }

    fun submitDonation(token : String, mosqueId: Int, jmlDonasi: String, donationDate: String,jnsDonasi: Int, bankTujuan: String) {
        loading.value = true
        disposable.add(Services.getPostDonation().donationSubmit("Bearer $token", mosqueId.toString(),"1",bankTujuan,donationDate,jnsDonasi.toString(),jmlDonasi.toInt(),0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ donationRespons ->
                    println("SUBMIT RESP ${donationRespons}")

                    mosquesData.value = donationRespons
                    masjidLoadError.value = false
                    loading.value = false
                },{ err->
                    masjidLoadError.value = true
                    loading.value = false
                }))

    }
}