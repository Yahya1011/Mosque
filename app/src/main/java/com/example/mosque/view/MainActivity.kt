package com.example.mosque.view


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.os.Bundle
import android.os.IBinder
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

class MainActivity : AppCompatActivity() {

    lateinit var receiver: LocationReceiver
    lateinit var service: LocationService
    lateinit var viewModel: MainViewModel
    private var isBound: Boolean = false
    private val jadwalShalatAdapter = JadwalSholatAdapter(ArrayList())
    private var connection  = object : ServiceConnection{
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

        rv_jadwal_sholat.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = jadwalShalatAdapter
        }


    }


    private fun observeViewModel() {
        viewModel.isGpsOnBoolean.observe(this, Observer{
            if (it) {
                provideLocation()
            } else {
                viewModel.turnOnGps(this)
            }
        })

        viewModel.dataJadwal.observe(this, Observer{ mosques ->
            mosques?.let {
                println("DATA ${it.items.size}")
                rv_jadwal_sholat.visibility = View.VISIBLE
                waktu_sholat.visibility = View.VISIBLE
                jam_sholat.visibility = View.VISIBLE
                jadwalShalatAdapter.updateJadwal(it.items)
                val strTime = getCurrentTime()
                when (strTime) {
                    it.items[0].fajar -> {
                        waktu_sholat.text = "Fajar"
                        jam_sholat.text = convertTime(it.items[0].fajar)
                    }
                    it.items[0].subuh -> {
                        waktu_sholat.text = "Shubuh"
                        jam_sholat.text = convertTime(it.items[0].subuh)
                    }
                    it.items[0].zuhur -> {
                        waktu_sholat.visibility = View.VISIBLE
                        jam_sholat.visibility = View.VISIBLE
                        waktu_sholat.text = "Dzuhur"
                        jam_sholat.text = convertTime(it.items[0].zuhur)
                    }
                    it.items[0].ashar -> {
                        waktu_sholat.text = "Ashar"
                        jam_sholat.text = convertTime(it.items[0].ashar)
                    }
                    it.items[0].maghrib -> {
                        waktu_sholat.text = "Magrib"
                        jam_sholat.text = convertTime(it.items[0].maghrib)
                    }
                    it.items[0].isya -> {
                        waktu_sholat.text = "Isya"
                        jam_sholat.text = convertTime(it.items[0].isya)
                    }
                }

            }
        })

        viewModel.jadwalLoadError.observe(this, Observer { isError->
            isError?.let {
                error_jadwal.visibility = if(it) View.VISIBLE else View.GONE
                error_waktu_shalat.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer { isLoading->
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


    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(
            receiver,
            IntentFilter(ACTION_BROADCAST)
        )
    }


    override fun onStart() {
        super.onStart()
        bindService(Intent(this, LocationService::class.java), connection, Context.BIND_AUTO_CREATE)
        observeViewModel()
    }




    override fun onStop() {
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
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