package com.example.mosque.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mosque.model.Mosque
import com.example.mosque.model.MosquesModel
import com.example.mosque.network.MosqueApi
import io.reactivex.disposables.CompositeDisposable


class MosqueDataSourceFactory(private val apiService : MosqueApi, private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int, Mosque>(){

    val mosqueLiveDataSource = MutableLiveData<MosqueDataSource>()

    override fun create(): DataSource<Int, Mosque> {
        val mosqueDataSource = MosqueDataSource(apiService, compositeDisposable)
        mosqueLiveDataSource.postValue(mosqueDataSource)
        return mosqueDataSource
    }

}
