package com.example.mosque.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mosque.common.Constans
import com.example.mosque.model.Mosque
import com.example.mosque.model.MosquesModel
import com.example.mosque.network.MosqueApi
import com.example.mosque.utils.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MosquePagedListRepository(private val apiService : MosqueApi){
    lateinit var moviePagedList: LiveData<PagedList<Mosque>>
    lateinit var moviesDataSourceFactory: MosqueDataSourceFactory

    fun fetchLiveMosquePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Mosque>> {
        moviesDataSourceFactory = MosqueDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constans.POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MosqueDataSource, NetworkState>(
            moviesDataSourceFactory.mosqueLiveDataSource, MosqueDataSource::networkState)
    }
}