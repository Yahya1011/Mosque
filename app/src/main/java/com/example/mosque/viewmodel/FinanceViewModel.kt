package com.example.mosque.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mosque.model.LaporanModel
import com.example.mosque.network.Services
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FinanceViewModel :  ViewModel(){

    private val disposable = CompositeDisposable()
    val financeLoadError = MutableLiveData<Boolean>()
    val keuanganData = MutableLiveData<List<LaporanModel>>()
    val loading = MutableLiveData<Boolean>()


    /*fun refreshKeuangan(id: Int){
        fetchKeuangan(id)
    }

    private fun fetchKeuangan(id: Int) {
        loading.value = true
        println("data = $id")
        disposable.add(
            Services.getLaporanDetail().getDetailLaporan(id.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    println("KEUANGAN RESP ${it.data.size}")

                    keuanganData.value = it.data
                    keuanganLoadError.value = false
                    loading.value = false
                },{ err->
                    keuanganLoadError.value = true
                    loading.value = false
                }))
    }*/

    fun submitFinance(token : String , category_id: Int, sub_category_id: Int, information: String, date: String,nominal: String) {
        loading.value = true
        disposable.add(Services.getPostFinance().financeSubmit("Bearer $token", category_id,sub_category_id ,information,date,nominal.toInt())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                println("SUBMIT RESP ${it.message}")
                financeLoadError.value = false
                loading.value = false
            },{ err->
                financeLoadError.value = true
                loading.value = false
            }))

    }

}