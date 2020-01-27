package com.example.mosque.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mosque.model.Mosque
import com.example.mosque.model.respons.ApiRespons
import com.example.mosque.network.Services
import com.example.mosque.repository.MosqueDataSource
import com.example.mosque.repository.MosqueDataSourceFactory
import com.example.mosque.repository.MosquePagedListRepository
import com.example.mosque.utils.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModels(private val mosqueRepository : MosquePagedListRepository) : ViewModel(){

    private val compositeDisposable = CompositeDisposable()

    val  mosquePagedList : LiveData<PagedList<Mosque>> by lazy {
        mosqueRepository.fetchLiveMosquePagedList(compositeDisposable)
    }

    val  networkState : LiveData<NetworkState> by lazy {
        mosqueRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return mosquePagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}