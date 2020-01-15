package com.example.mosque.common

import com.example.mosque.network.ApiEndPoint

object Constans {

    const val imageUrlPath : String = ApiEndPoint.BASE_URL + "uploads/"
    const val KEYAPI: String = "8e73c89b39aa03da7b1a1c97b57d531f"
    const val LOG_TAG = "LOG_TAG"

    const val LOCATION_REQUEST = 100
    const val GPS_REQUEST = 101

    const val GPS_UPDATE_TIME: Long = 1000
    const val SMALLEST_DISTANCE: Float = 10f

    const val CHANNEL_ID = "channel_location"
    const val CHANNEL_NAME = "channel_location_name"
    const val NOTIFICATION_ID = 10111101

    const val ACTION_BROADCAST = "loc_broadcast"
    const val EXTRA_LOCATION = "loc_extra"

    const val PREF_KEY_SERVICE_NAME = "SERVICE_KEY"
    const val PREF_KEY_SERVICE_STATE = "SERVICE_STATE"

    enum class ServiceState {
        STARTED,
        STOPPED,
    }

    enum class Actions {
        START,
        STOP
    }
}