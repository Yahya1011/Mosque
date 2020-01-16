package com.example.mosque.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mosque.R
import com.example.mosque.model.MainNav
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainNavViewModel  : ViewModel(){
    private val disposable = CompositeDisposable()
    var dataNavigation = MutableLiveData<List<MainNav>>()
    var navLoadError = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()

    fun refresh(context: Context) {
        fetchNavigation(context)
    }

    private fun fetchNavigation(context: Context) {
        loading.value = true
        disposable.add(loadDataNavigation(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({navMenuRespons ->
                println("NAVI RESP ${navMenuRespons.size}")
                dataNavigation.value = navMenuRespons
                navLoadError.value = false
                loading.value = false
            },{err->
                navLoadError.value = true
                loading.value = false
            })
        )

    }

    private fun loadDataNavigation(context: Context) : Observable<List<MainNav>> {
        return Observable.just(createMenuItem(context))
    }

    private fun createMenuItem(context: Context): MutableList<MainNav> {
        val navMenu = mutableListOf<MainNav>()
        val onMenuText = context.resources.getStringArray(R.array.menuText)
        val onMenuIcon = context.resources.obtainTypedArray(R.array.menuIcon)

        // loop and set data to model
        for(i in onMenuText.indices) {
            navMenu.add(
                MainNav(
                    onMenuIcon.getResourceId(i,0),
                    onMenuText[i]
                ))
        }
        onMenuIcon.recycle()
        return navMenu
    }


}