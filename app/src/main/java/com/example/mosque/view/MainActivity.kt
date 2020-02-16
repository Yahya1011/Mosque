package com.example.mosque.view


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mosque.R
import com.example.mosque.adapter.JadwalSholatAdapter
import com.example.mosque.adapter.NavMainAdapter
import com.example.mosque.common.Constans
import com.example.mosque.common.Constans.ACTION_BROADCAST
import com.example.mosque.common.Constans.LOCATION_REQUEST
import com.example.mosque.helper.AppPreferencesHelper
import com.example.mosque.model.MainNav
import com.example.mosque.utils.*
import com.example.mosque.view.activity.AcaraActivity
import com.example.mosque.viewmodel.MainNavViewModel
import com.example.mosque.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    lateinit var mPrefData: AppPreferencesHelper
    private var updateTime: String? = null
    private var futureDate: String? = null
    lateinit var receiver: LocationReceiver
    lateinit var service: LocationService
    lateinit var viewModel: MainViewModel
    lateinit var mainViewModel: MainNavViewModel
    private var isBound: Boolean = false
    private val jadwalShalatAdapter = JadwalSholatAdapter(ArrayList())
    private val navMainAdapter = NavMainAdapter(ArrayList())
    private val COUNTDOWN_UPDATE_INTERVAL: Long = 500
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

        mPrefData = AppPreferencesHelper(this)
        println("Data Pref Main  ${mPrefData.isLoginIn()}")
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.turnOnGps(this)
        mainViewModel = ViewModelProvider(this)[MainNavViewModel::class.java]
        mainViewModel.refresh(this)

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

        rv_nav_content.apply{
            addItemDecoration(EqualSpacingItemDecoration(12, EqualSpacingItemDecoration.HORIZONTAL))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = navMainAdapter
        }

        observeMainNavModel()

        initNavigationListener()
    }

    private fun initNavigationListener() {
        nav.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.bantuan -> {
                        return true
                    }
                    R.id.berita -> {
                        return true
                    }
                    R.id.beranda -> {
                        return true
                    }
                    R.id.transaksi -> {
                        return true
                    }
                    R.id.akun -> {
                        if(!mPrefData.isLoginIn()){
                            selectActivity()
//                            println("Silahkan Login atau Daftar Terlebih Dahulu")
//                            showDialog("Silahkan Login atau Daftar Terlebih Dahulu",400)
                        } else{
                            loadProfileDashboard()
                        }
                        return true
                    }
                }
                return false
            }

        })
    }

    private fun loadProfileDashboard() {
        startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
    }

    fun showDialog(msg: String, code: Int){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(msg)
            .setCancelable(false)
            .setPositiveButton("ok") {dialog, id ->

                selectActivity()
            }

        val alert = dialogBuilder.create()
        alert.setTitle("Maaf anda belum login")
        alert.show()
    }

    private fun selectActivity() {
        if (mPrefData.getAccessToken() !=null || !mPrefData.isLoginIn() )  {
            openLoginActivity()
        } else if (mPrefData.getAccessToken() == null){
            openRegisterActivity()
        }
    }

    private fun openLoginActivity() {
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
    }

    private fun openRegisterActivity() {
        startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
    }


    private fun observeMainNavModel() {
        mainViewModel.dataNavigation.observe(this, Observer { mainNavs->
            mainNavs?.let {
                println("DATA NAV ${it.size}")
                rv_nav_content.visibility = View.VISIBLE
                navMainAdapter.updateNav(it)
                navMainAdapter.setOnItemClickListener(object : NavMainAdapter.OnItemClickListener{
                    override fun onItemSelected(mainNav: MainNav) {
                        initClickListener(mainNav.nav_name)
                    }

                })


            }

        })
    }

    private fun initClickListener(navName: String) {

        if (navName == "Masjid Sekitar"){
            val intent = Intent(this, MasjidSekitarActivity::class.java)
            intent.putExtra("key", navName)
            startActivity(intent)
        } else if(navName == "Donasi"){
            val intent = Intent(this, DonasiActivity::class.java)
            intent.putExtra("key", navName)
            startActivity(intent)
        } else if(navName == "Informasi"){
            val intent = Intent(this, InformasiActivity::class.java)
            intent.putExtra("key", navName)
            startActivity(intent)
        } else if(navName == "Acara"){
            val intent = Intent(this, AcaraActivity::class.java)
            intent.putExtra("key", navName)
            startActivity(intent)
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
                } else if (timeOfDay in 12..14) {
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
        return futureDate
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
    private fun updateCountdown(): Runnable = Runnable {

        try {
            counting_time.visibility = View.VISIBLE
            val convertTime = futureDate?.let { mTimeDifference(getCurrentTime(), it) }
            if (convertTime != null) {
                updateString(convertTime)
            }
            //counting_time.text =
        } finally {
            countdownHandler?.postDelayed(updateCountdown(), COUNTDOWN_UPDATE_INTERVAL)
        }
    }

    private fun updateString(mTimeDifference: String) {
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