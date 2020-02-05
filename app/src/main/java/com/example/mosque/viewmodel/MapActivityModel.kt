package com.example.mosque.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mosque.model.Mosque
import com.example.mosque.model.respons.ApiRespons
import com.example.mosque.network.MosqueApi
import com.example.mosque.network.Services
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MapActivityModel(application: Application): BaseViewModel(application) {
    val masjidsList = MutableLiveData<List<Mosque>>()
    val masjidsListError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    val jsonApi: MosqueApi = Services.getHomeMosque()
    private val myCompositeDisposable = CompositeDisposable()

    fun fetchData(url:String) {
        loading.value = true
        myCompositeDisposable.add(jsonApi.getMosqueMarker()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({myPlaces -> displayData(myPlaces)} ,
                { t: Throwable -> Log.e("RxError : ","RxError : " + t.message)
                    masjidsListError.value = true
                    loading.value = false
                })
        )
    }
    private fun displayData(myPlaces: ApiRespons.MosquesRespons){

        if (myPlaces.message.equals("Successfully!")){
            val masjids: List<Mosque>? = myPlaces.data.data
            masjidsList.value = masjids!!.toList()
            masjidsListError.value = false
            loading.value = false
            (masjids.toList())

        }else{
            masjidsListError.value = true
            loading.value = false
        }
        println("marker1 ${myPlaces}")

    }
}