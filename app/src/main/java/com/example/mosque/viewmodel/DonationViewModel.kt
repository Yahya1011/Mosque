package com.example.mosque.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mosque.model.Mosque
import com.example.mosque.model.respons.ApiRespons
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

    val successSubmit = MutableLiveData<ApiRespons.DonationResponds>()

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
                    mosquesData.value = mosqueRespons.data
                    masjidLoadError.value = false
                    loading.value = false
                },{ err->
                    masjidLoadError.value = true
                    loading.value = false
                }))
    }

    fun submitDonation(token : String , mosqueId: Int, userId: Int, jmlDonasi: String, donationDate: String,jnsDonasi: Int, bankTujuan: String) {
        loading.value = true
        disposable.add(Services.getPostDonation().donationSubmit("Bearer $token", mosqueId,userId ,bankTujuan,donationDate,jnsDonasi,jmlDonasi.toInt(),0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ donationRespons ->
                    println("SUBMIT RESP ${donationRespons}")

                    successSubmit.value = donationRespons
                    masjidLoadError.value = false
                    loading.value = false
                },{ err->
                    masjidLoadError.value = true
                    loading.value = false
                }))

    }

}