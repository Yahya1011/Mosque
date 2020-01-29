package com.example.mosque.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mosque.common.Constans.FIRST_PAGE
import com.example.mosque.model.Mosque
import com.example.mosque.network.MosqueApi
import com.example.mosque.network.Services
import com.example.mosque.utils.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MosqueDataSource (private val apiService : MosqueApi, private val disposable: CompositeDisposable) : PageKeyedDataSource<Int, Mosque>() {

    var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    val service = Services.getHomeMosque()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Mosque>
    ) {
        networkState.postValue(NetworkState.LOADING)
        disposable.add(service.getMosque(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onResult(it.data.data, page-1, page+1)
                networkState.postValue(NetworkState.LOADED)
            },{ err->
                networkState.postValue(NetworkState.ERROR)
            })
        )



    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Mosque>) {
        disposable.add(service.getMosque(params.key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                if (it.data.total >= params.key){
                    callback.onResult(it.data.data, params.key+1)
                    networkState.postValue(NetworkState.LOADED)
                }
            },{ err->
                networkState.postValue(NetworkState.ERROR)
            })
        )
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Mosque>) {

    }












}
