package com.example.mosque.viewmodel

import android.content.Context
import android.content.res.Resources
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mosque.R
import com.example.mosque.common.Constans
import com.example.mosque.model.Jadwal
import com.example.mosque.model.LocationDataModel
import com.example.mosque.model.LocationModel
import com.example.mosque.model.MainNav
import com.example.mosque.network.Services
import com.example.mosque.utils.LocationUtils
import com.example.mosque.utils.getLatitudeText
import com.example.mosque.utils.getLocationTitle
import com.example.mosque.utils.getLongitudeText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*


class MainViewModel : ViewModel() {


    private val disposable = CompositeDisposable()
    var dataModel = MutableLiveData<LocationDataModel>()
    var dataJadwal = MutableLiveData<Jadwal>()
    var jadwalLoadError = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
    private var isGpsOn: MutableLiveData<Boolean> = MutableLiveData()

    var isGpsOnBoolean = isGpsOn.apply {
        this.value
    }




    fun turnOnGps(context: Context) {
        LocationUtils().turnGPSOn(context) {
            isGpsOn.value = it
        }
    }






    fun sendLocationData(context: Context, location: LocationModel) {
        updateData(context, location)
        var addressName = getRegionName(context, location.latitude, location.longitude).apply {
            fetchLocation(this)
        }
    }

    private fun fetchLocation(address: String) {
        loading.value = true
        disposable.add(Services.getLocation().getJadwalSholat(address, Constans.KEYAPI)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({jadwalRespons ->
                println("LOCATION DATA ${jadwalRespons}")
                dataJadwal.value = jadwalRespons
                jadwalLoadError.value = false
                loading.value = false


            },{
                jadwalLoadError.value = true
                loading.value = false
            })
        )
    }

    private fun updateData(context: Context, location: LocationModel) {
        dataModel.value = location.mapToData(context)
    }

    private fun LocationModel.mapToData(context: Context): LocationDataModel {
        return LocationDataModel(
            txUpdated = getLocationTitle(context),
            txLatitude = getLatitudeText(context, this),
            txLongitude = getLongitudeText(context, this)
        ).apply {
            println( this.toString() )
        }
    }

    fun getRegionName(context: Context, lati: Double, longi: Double): String {
        var regioName = ""
        val gcd = Geocoder(context, Locale.getDefault())
        try {
            val addresses: List<Address> = gcd.getFromLocation(lati, longi, 1)
            if (addresses.isNotEmpty()) {
                println("DATA ${addresses[0].subAdminArea}")
                regioName = addresses[0].subAdminArea
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return regioName
    }




    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}

