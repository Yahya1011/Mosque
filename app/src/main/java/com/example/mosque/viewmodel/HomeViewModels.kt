package com.example.mosque.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mosque.model.Masjid
import com.example.mosque.network.Services
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class HomeViewModels : ViewModel(){

    private val disposable = CompositeDisposable()
    val masjids = MutableLiveData<List<Masjid>>()
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
                println(mosqueRespons.size)
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