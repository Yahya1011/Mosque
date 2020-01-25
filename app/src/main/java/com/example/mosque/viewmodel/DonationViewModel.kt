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
                },{ err->
                    masjidLoadError.value = true
                    loading.value = false
                }))
    }

    fun submitDonation(mosqueId: Int, jmlDonasi: String, donationDate: String,jnsDonasi: Int, bankTujuan: String) {
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIyIiwianRpIjoiNDdlOTgyZmEwYTk3NDNlODgwNDYyYWZkZDdhOGM1NTU2ZDkxNzM3ZWIyZGM4NWNjMTg2MWE0MDg0M2VkZWUzNjhlNzRiOTYyNDk3MzMzYjYiLCJpYXQiOjE1NzkxNzEyNTgsIm5iZiI6MTU3OTE3MTI1OCwiZXhwIjoxNjEwNzkzNjU4LCJzdWIiOiI3OCIsInNjb3BlcyI6W119.e9hxgHXc784Pnkq5uPAPvZdHWL5rq2s-qLCi5bGk4HZt5PkrjzdoEYa21uQei3F5xaj0zgLO5qMTBJel46R_rcCAV-m1NvKgkT-02x3VxVUimbDGeko7Fc_vu_s1WWHt9qaCYsGnUNj0BuY0FO8FNLJgSIkQCM_c47hx0pyWEZ-aedavg0aJ6VrGc4X6-eMZ0pEc47cI82fq2mshiVdX28V2paVpegCsst1bVKRh17UvdHQCg9Xv0vcN1QDl4eBH517RPiJwWmVlVqZIJ3TDf_oSRf8m-oqz9D60PW_NAeXTT_SKRUppJtKF3HXf31f7t1NgAffGg07O754isW2DV6FbmgMgKctJ8mQL7q-fpmFCHSsFeCS858NVX0D3QA0oFMpTBmI8qXF_fm7AESQuk5H9ESPPwElqhWwRNATPRDyuPxFbB1JJRmTx4U9N_M1I5lBYla4LrHvwCUJZZApSEPyJq6F_Jikn_mstjhi9CRd-fP3mN2pkyyhrwELCtl6PwjLkWL5PX7WP_L4026wu4XZGZm79nDtf8b2W-jpGtCtvw38ZHztq6nu4KGJxgh_I3NM58MyTdjW_0GHYBsTH8fcZVt0BLwcc2Evp-l0lMlmSpptHoIeTzgJtsmUeFpHWXn8YIKUCotIIZQnmXbDvozoEubHig6Isw7nA6uWln1w"
        loading.value = true
        disposable.add(Services.getPostDonation().donationSubmit("Bearer $token", mosqueId.toString(),"1",bankTujuan,donationDate,jnsDonasi.toString(),jmlDonasi.toInt(),0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ donationRespons ->
                    /*println("MOSQUE RESP ${donationRespons}")*/

                    mosquesData.value = donationRespons
                    masjidLoadError.value = false
                    loading.value = false
                },{ err->
                    masjidLoadError.value = true
                    loading.value = false
                }))

    }
}