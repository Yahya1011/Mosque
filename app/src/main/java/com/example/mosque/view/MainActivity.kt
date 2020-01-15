package com.example.mosque.view


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mosque.R
import com.example.mosque.adapter.JadwalSholatAdapter
import com.example.mosque.common.Constans
import com.example.mosque.common.Constans.ACTION_BROADCAST
import com.example.mosque.common.Constans.LOCATION_REQUEST
import com.example.mosque.utils.*
import com.example.mosque.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private var updateTime: String? = null
    private var futureDate: String? = null
    lateinit var receiver: LocationReceiver
    lateinit var service: LocationService
    lateinit var viewModel: MainViewModel
    private var isBound: Boolean = false
    private val jadwalShalatAdapter = JadwalSholatAdapter(ArrayList())
    private val COUNTDOWN_UPDATE_INTERVAL : Long = 500
    private var countdownHandler: Handler? = null
    private var connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as LocationService.LocalBinder
            this@MainActivity.service = binder.getService()
            isBound = true
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.turnOnGps(this)



        receiver = LocationReceiver {
            viewModel.sendLocationData(this, it)
            /*if (service.isForeground()) {
                service.updateData()
            }*/
        }

        rv_jadwal_sholat.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = jadwalShalatAdapter
        }


    }


    private fun observeViewModel() {
        viewModel.isGpsOnBoolean.observe(this, Observer {
            if (it) {
                provideLocation()
            } else {
                viewModel.turnOnGps(this)
            }
        })

        viewModel.dataJadwal.observe(this, Observer { mosques ->
            mosques?.let {
                println("DATA ${it.items.size}")
                rv_jadwal_sholat.visibility = View.VISIBLE
                jadwalShalatAdapter.updateJadwal(it.items)
                waktu_sholat.visibility = View.VISIBLE
                jam_sholat.visibility = View.VISIBLE
                counting_time.visibility = View.VISIBLE
                val calendar = Calendar.getInstance()
                val timeOfDay = calendar.get(Calendar.HOUR_OF_DAY)
                if (timeOfDay in 4..5) {
                    waktu_sholat.text = "Fajar"
                    //greetImg?.setImageResource(R.drawable.img_default_half_morning)
                    it.items[0].fajar.let {
                        jam_sholat.text = convertTime(it)

                    }
                    getFutureDate(it.items[0].subuh)
                    startCountdown()
                } else if (timeOfDay in 5..6) {
                    waktu_sholat.text = "Subuh"
                    it.items[0].subuh.let {
                        jam_sholat.text = convertTime(it)
                    }

                    getFutureDate(it.items[0].zuhur)
                    startCountdown()
                } else if (timeOfDay in 7..9) {
                    waktu_sholat.text = "Dhuha"
                }else if (timeOfDay in 12..14) {
                    waktu_sholat.text = "Dzuhur"
                    it.items[0].zuhur.let {
                        jam_sholat.text = convertTime(it)
                    }
                    getFutureDate(it.items[0].ashar)
                    startCountdown()
                } else if (timeOfDay in 15..17) {
                    waktu_sholat.text = "Ashar"
                    it.items[0].ashar.let {
                        jam_sholat.text = convertTime(it)
                    }
                    getFutureDate(it.items[0].maghrib)
                    startCountdown()
                } else if (timeOfDay in 18..19) {
                    waktu_sholat.text = "Maghrib"
                    it.items[0].maghrib.let {
                        jam_sholat.text = convertTime(it)
                    }
                    getFutureDate(it.items[0].isya)
                    startCountdown()
                } else if (timeOfDay in 19..24) {
                    waktu_sholat.text = "Isya"
                    it.items[0].isya.let {
                        jam_sholat.text = convertTime(it)
                    }

                    getFutureDate(it.items[0].fajar)
                    startCountdown()
                } else {
                    waktu_sholat.text = "Shalat Sunnah Malam"
                    waktu_sholat.gravity = Gravity.CENTER_VERTICAL
                    jam_sholat.visibility = View.GONE
                    getFutureDate(it.items[0].fajar)
                    startCountdown()


                }



            }
        })

        viewModel.jadwalLoadError.observe(this, Observer { isError ->
            isError?.let {
                error_jadwal.visibility = if (it) View.VISIBLE else View.GONE
                error_waktu_shalat.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                progressJadwal.visibility = if (it) View.VISIBLE else View.GONE
                progressWaktuSholat.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    error_jadwal.visibility = View.GONE
                    error_waktu_shalat.visibility = View.GONE
                    waktu_sholat.visibility = View.GONE
                    jam_sholat.visibility = View.GONE
                    rv_jadwal_sholat.visibility = View.GONE
                }
            }
        })
    }

    private fun getFutureDate(str: String): String? {
        futureDate = convertTime(str)
        println("MAIN ACTIVITY $futureDate")
        return  futureDate
    }


    private fun startCountdown() {
        stopCountdown()
        countdownHandler = Handler()
        updateCountdown().run()
    }

    private fun stopCountdown() {
        if (countdownHandler != null) {
            countdownHandler!!.removeCallbacks(updateCountdown())
            countdownHandler = null
        }
    }

    /**
     * Updates the countdown.
     */
    private fun updateCountdown() : Runnable  = Runnable {

        try {
            counting_time.visibility = View.VISIBLE
            val convertTime = futureDate?.let { mTimeDifference(getCurrentTime(), it) }
            println("Update Count ${convertTime}")
            if (convertTime != null) {
                updateString(convertTime)
            }
            //counting_time.text =
        } finally {
            countdownHandler?.postDelayed(updateCountdown(), COUNTDOWN_UPDATE_INTERVAL)
        }
    }

    private fun updateString(mTimeDifference: String)  {
        println("Update Count  == $mTimeDifference")
        updateTime = mTimeDifference
        counting_time.text = mTimeDifference
    }

    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(
            receiver,
            IntentFilter(ACTION_BROADCAST)
        )
    }


    override fun onStart() {
        super.onStart()
        startCountdown()
        bindService(Intent(this, LocationService::class.java), connection, Context.BIND_AUTO_CREATE)
        observeViewModel()
    }


    override fun onStop() {
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
        stopCountdown()
        super.onStop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            provideLocation()
        }
    }


    @SuppressLint("InlinedApi")
    private fun provideLocation() {
        when {
            isPermissionsGranted(this) -> {
                actionOnService(Constans.Actions.START)
            }

            else -> ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                LOCATION_REQUEST
            )
        }
    }


    private fun actionOnService(action: Constans.Actions) {
        if (getServiceState(this) == Constans.ServiceState.STOPPED && action == Constans.Actions.STOP) return
        Intent(this, LocationService::class.java).also {
            println("Starting the service")
            it.action = action.name
            startService(it)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                provideLocation()
            }
        }
    }
}