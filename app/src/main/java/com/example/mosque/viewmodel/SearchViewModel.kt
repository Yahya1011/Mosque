package com.example.mosque.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mosque.common.Constans
import com.example.mosque.model.Mosque
import com.example.mosque.network.Services
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {

    var page = Constans.FIRST_PAGE
    private val disposable = CompositeDisposable()
    val masjid = MutableLiveData<List<Mosque>>()
    val masjidLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchMasjid()
    }



    //GET LIST VIEW MASJID
    private fun fetchMasjid() {
        loading.value = true
        disposable.add(
            Services.getHomeMosque().getMosqueDummy(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ mosqueRespons ->
                    //                println("Mosque ${mosqueRespons}")
                    masjid.value = mosqueRespons.data
                    masjidLoadError.value = false
                    loading.value = false
                }, { err ->
                    masjidLoadError.value = true
                    loading.value = false
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}