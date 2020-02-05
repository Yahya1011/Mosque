package com.example.mosque.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mosque.model.Fasilitas
import com.example.mosque.model.Mosque
import com.example.mosque.model.respons.ApiRespons
import com.example.mosque.network.Services
import com.example.mosque.repository.MosquePagedListRepository
import com.example.mosque.utils.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModels(private val mosqueRepository : MosquePagedListRepository) : ViewModel(){

    private val compositeDisposable = CompositeDisposable()

    lateinit var masjid: LiveData<PagedList<Mosque>>
    val fasilitasData = MutableLiveData<List<Fasilitas>>()
    val masjidLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val successSubmit = MutableLiveData<ApiRespons.FilterRespons>()

    val  mosquePagedList : LiveData<PagedList<Mosque>> by lazy {
        mosqueRepository.fetchLiveMosquePagedList(compositeDisposable)
    }

    val  networkState : LiveData<NetworkState> by lazy {
        mosqueRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return mosquePagedList.value?.isEmpty() ?: true
    }

    fun loadFasilitas() {
        fetchFacilities()
    }

    //GET LIST FASILITAS
    private fun fetchFacilities() {
        loading.value = true
        compositeDisposable.add(
            Services.getFacilitiesList().getFilteredMasjid()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ facilitiesRespon ->
                    fasilitasData.value = facilitiesRespon
//                    println("DATA FASILITIES ${facilitiesRespon.size}")
                }, { err ->
                    //                println("DATA ${err.message}")
                })
        )
    }

    //get Filter Data
    fun submitFilter(full_time: String, ac: String, free_water: String, easy_access: String) {
        loading.value = true
        compositeDisposable.add(
            Services.getPostFilter().filterSubmit(full_time, ac, free_water, easy_access)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ filterRespons ->
                    println("SUBMIT RESP ${filterRespons}")

                    successSubmit.value = filterRespons
                    masjidLoadError.value = false
                    loading.value = false
                },{ err->
                    masjidLoadError.value = true
                    loading.value = false
                }))

    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}