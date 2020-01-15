package com.example.mosque.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.mosque.common.Constans.EXTRA_LOCATION
import com.example.mosque.model.LocationModel

class LocationReceiver(val function: (LocationModel) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val location: LocationModel =
            intent.getSerializableExtra(EXTRA_LOCATION) as LocationModel
        println("Receive New Location: $location")
        function(location)
    }
}