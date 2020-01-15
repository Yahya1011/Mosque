package com.example.mosque.utils

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.mosque.common.Constans
import com.example.mosque.common.Constans.ACTION_BROADCAST
import com.example.mosque.common.Constans.EXTRA_LOCATION
import com.example.mosque.model.LocationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LocationService : Service() {
    private var isForeground = false
    private var isServiceStarted = false
    private val localBinder = LocalBinder()
    private var wakeLock: PowerManager.WakeLock? = null
    private var currentLocation: LocationModel? = null

    inner class LocalBinder : Binder() {
        fun getService(): LocationService {
            return this@LocationService
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        println("Service onBind")
        stopForeground(true)
        isForeground = false
        return localBinder
    }

    override fun onRebind(intent: Intent?) {
        println("Service onRebind")
        stopForeground(true)
        isForeground = false
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        if (isServiceStarted && !isForeground) {
            println("Service unBind")
            isForeground = true
        }
        return true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("Service onStartCommand, startId: $startId")
        if (intent != null) when (intent.action) {
            Constans.Actions.START.name -> startService()
            Constans.Actions.STOP.name -> stopService()
            else ->
                println("No action!!!")
        } else {
            println("Service onStartCommand with a null intent")
        }
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        println("Service onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        isForeground = false
        println("Service onDestroy")
    }

    fun isForeground(): Boolean {
        return isForeground
    }

    private fun startService() {
        if (isServiceStarted) return
        isServiceStarted = true
        setServiceState(this, Constans.ServiceState.STARTED)

        wakeLock =
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "LocationService::lock").apply {
                    acquire(10 * 60 * 1000L /*10 minutes*/)
                }
            }

        GlobalScope.launch(Dispatchers.IO) {
            while (isServiceStarted) {
                launch(Dispatchers.IO) {
                    println("Start Update")
                    startLocationUpdate()
                }
                delay( 300000)
            }
        }
    }

    private fun startLocationUpdate() {
        LocationUtils().updateByFusedLocation(this@LocationService) {
            println("New Location: $it")
            currentLocation = it
            sendUpdateBroadcast(it)
        }
    }

    private fun sendUpdateBroadcast(location: LocationModel) {
        val intent = Intent(ACTION_BROADCAST)
        intent.putExtra(EXTRA_LOCATION, location)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
    }

    private fun stopService() {
        try {
            wakeLock?.let {
                if (it.isHeld) {
                    it.release()
                }
            }
            stopForeground(true)
            stopSelf()
        } catch (e: Exception) {
            println("Service stopped without starting: ${e.message}")
        }
        isForeground = false
        isServiceStarted = false
        setServiceState(this, Constans.ServiceState.STOPPED)
    }

}