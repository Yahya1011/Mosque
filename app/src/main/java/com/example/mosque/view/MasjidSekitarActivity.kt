package com.example.mosque.view

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mosque.R
import com.example.mosque.adapter.FasilitasAdapter
import com.example.mosque.adapter.MasjidFasilitasAdapter
import com.example.mosque.adapter.MasjidSekitarAdapter
import com.example.mosque.adapter.SearchEngineAdapter
import com.example.mosque.common.Constans
import com.example.mosque.model.Mosque
import com.example.mosque.network.MosqueApi
import com.example.mosque.network.Services
import com.example.mosque.repository.MosquePagedListRepository
import com.example.mosque.utils.CustomProgressBar
import com.example.mosque.utils.EqualSpacingItemDecoration
import com.example.mosque.utils.MapsUtils
import com.example.mosque.utils.MapsUtils.Companion.getUrl
import com.example.mosque.utils.NetworkState
import com.example.mosque.viewmodel.HomeViewModels
import com.example.mosque.viewmodel.MapActivityModel
import com.example.mosque.viewmodel.MasjidSekitarViewModel
import com.example.mosque.viewmodel.SearchViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_masjid_sekitar.*
import kotlinx.android.synthetic.main.row_masjidsekitar.*
import kotlinx.android.synthetic.main.row_searchengine.*
import kotlinx.android.synthetic.main.row_searchengine.jarakTV

class MasjidSekitarActivity : AppCompatActivity(), OnMapReadyCallback {

    //filterasi
    internal lateinit var restApi: MosqueApi //get list fasilitas.
    lateinit var fasilitasAdapter: MasjidFasilitasAdapter
    private val searchAdapter =
        SearchEngineAdapter(ArrayList())
    //MAPS
    private lateinit var mMap: GoogleMap
    private lateinit var mLastLocation: Location
    private var mMarker: Marker? = null

    private lateinit var mapsModel: MapActivityModel

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    //Location
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    //Get List Masjid
    lateinit var sekitarAdapter: MasjidSekitarAdapter
    lateinit var viewModel: HomeViewModels
    lateinit var searchModel: MasjidSekitarViewModel
    lateinit var mosqueRepository: MosquePagedListRepository

    val progressBar = CustomProgressBar()
    private var myCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masjid_sekitar)

        //init
        restApi = Services.getFasilitasList() //filterasi
        Handler().post { fetchCategories() } //DAPETIN LIST FASILITAS MENGGUNAKAN API / RETROFIT
        val apiService: MosqueApi = Services.getHomeMosque()
        mosqueRepository = MosquePagedListRepository(apiService)
//        viewModel = getViewModels()
        searchModel = ViewModelProvider(this)[MasjidSekitarViewModel::class.java]
        mapsModel = ViewModelProvider(this).get(MapActivityModel::class.java)
        //end init


        nav.setOnNavigationItemSelectedListener { p0 ->
            when (p0.itemId) {
                R.id.btn_sort -> {
                    println("SORT BELOM AKTIF")
                }
                R.id.btn_filter -> {
                    showFilterDialog()
                }
            }
            true
        } // BUTTOM NAVIGATION

//        initAdapter() // GET LIST MASJID
        initSearch()
        initializeComponent()

        //actionbar
        setSupportActionBar(toolbar)
        //set back button
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            searchModel.refresh()
        }

        //Request runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkLocationPermission()) {
                accessMapLiveLocation()
            }
        } else {
            accessMapLiveLocation()
        }
        observeViewMapsModel() // ALL ABOUT GMAPS MARKER

    }



    //MELAKUKAN SHOW / POP-UP DIALOG DAN MENAMPILKAN LIST FASILITAS
    private fun showFilterDialog() {

        val alertDialog = AlertDialog.Builder(this@MasjidSekitarActivity)
        alertDialog.setTitle("Pilih Fasilitas")

        val inflater = this.layoutInflater
        val filter_options_layout = inflater.inflate(R.layout.dialog_filter, null)

        val recycler_options =
            filter_options_layout.findViewById(R.id.recycler_options) as RecyclerView
        recycler_options.setHasFixedSize(true)
        recycler_options.layoutManager = LinearLayoutManager(this)
        val adapter = FasilitasAdapter(baseContext, Services.categories!!)
        recycler_options.adapter = adapter

        alertDialog.setView(filter_options_layout)

        alertDialog.setNegativeButton("CANCEL") { dialogInterface, _ -> dialogInterface.dismiss() }
        alertDialog.setPositiveButton("FILTER") { dialogInterface, _ ->


            fetchFilterCategory(adapter.filterArray)
        }

        alertDialog.show()
    }

    //HASIL FASILITAS YANG TELAH DIPILIH AKAN TAMPIL DISINI
    private fun fetchFilterCategory(filterArray: String) {
        myCompositeDisposable.add(
            restApi.getFilteredMasjid(filterArray) //MANGGIL POSTNYA DISINI
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ filterCategories ->

                    map.isVisible == true
                    map.view?.visibility = View.GONE
                    txt_total.visibility = View.GONE
                    recycler_masjids.visibility = View.VISIBLE
                    recycler_masjids.adapter = MasjidFasilitasAdapter(
                        baseContext,
                        filterCategories
                    ) // HASIL DILEMPAR KE ADAPTER YANG BERBEDA
                            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

                    println("hasil filterasi $filterCategories")

                },
                    {
                        Toast.makeText(baseContext, "Masjid Tidak Ditemukan", Toast.LENGTH_SHORT).show()
                        recycler_masjids.visibility = View.VISIBLE
                    })
        )
    }

    //DAPETIN LIST FASILITAS DARI API
    private fun fetchCategories() {
        myCompositeDisposable.add(
            restApi.fasilitasList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ categories ->
                    Services.categories = categories
                    println("list $categories")
                },
                    {
                        Toast.makeText(baseContext, "Fail to connect /List Fasilitas", Toast.LENGTH_SHORT)
                            .show()
                    })
        )
    }
    //END FILTERASI

    //GET LIST MASJID
    /*private fun initAdapter() {
        sekitarAdapter = MasjidSekitarAdapter(this)
        recycler_masjids.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_masjids.addItemDecoration(
            EqualSpacingItemDecoration(
                12,
                EqualSpacingItemDecoration.VERTICAL
            )
        )
        recycler_masjids.adapter = sekitarAdapter
        viewModel.mosquePagedList.observe(this, Observer {
            sekitarAdapter.submitList(it)
        })


        viewModel.networkState.observe(this, Observer {
            //Load Progress Bar
            if (viewModel.listIsEmpty() && it == NetworkState.LOADING) {
                progressBar.show(this, "Please Wait...")
            } else {
                progressBar.dialog.dismiss()
            }

            if (!viewModel.listIsEmpty()) {
                sekitarAdapter.setNetworkState(it)
            }
        })
    }

    private fun getViewModels(): HomeViewModels {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModels(mosqueRepository) as T
            }
        })[HomeViewModels::class.java]
    }*/
    //END LIST MASJID


    //SEARCH OPTIONS
    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        //getting the search view from the menu
        val searchViewItem = menu!!.findItem(R.id.search)
        //getting the search view
        val searchView = searchViewItem.actionView as SearchView

        searchView.setOnSearchClickListener {
            if (map.isVisible == true) {
                txt_total.visibility = View.GONE
                recycler_masjids.visibility = View.INVISIBLE
                map.view?.visibility = View.GONE
                changeLayoutView()
            } else {
                recycler_masjids.visibility = View.GONE
                map.view?.visibility = View.GONE
            }
        }

        searchView.setOnCloseListener {
            if (map.isVisible == false) {
                txt_total.visibility = View.VISIBLE
                recycler_masjids.visibility = View.VISIBLE
                map.view?.visibility = View.VISIBLE
                changeBackLayoutView()
            }
            initSearch()
            false
        }

        searchView.maxWidth = Int.MAX_VALUE
        searchView.queryHint = "Cari Masjid Yuk"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                recycler_masjids.visibility = View.VISIBLE
                searchAdapter.filter.filter(query)
                println("DATA ACTIVITY $query")
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                recycler_masjids.visibility = View.GONE
                searchAdapter.filter.filter(newText)
                println("DATA ACTIVITY2 $newText")
                return false
            }
        })
        return true
    }

    private fun changeBackLayoutView() {
        val params: ViewGroup.LayoutParams = recycler_masjids.layoutParams
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        recycler_masjids.layoutParams = params
    }

    private fun changeLayoutView() {
        val params: ViewGroup.LayoutParams = recycler_masjids.layoutParams
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        recycler_masjids.layoutParams = params
    }
    //END SEARCH OPTIONS

    private fun initializeComponent() {
        searchModel.refresh()

        recycler_masjids.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                EqualSpacingItemDecoration(
                    12,
                    EqualSpacingItemDecoration.HORIZONTAL
                )
            )
            adapter = searchAdapter
        }
    }

    private fun initSearch() {
        searchModel.masjid.observe(this, Observer { masjid ->
            masjid?.let {
                recycler_masjids.visibility = View.VISIBLE
                txt_total.text = StringBuilder("Total Masjid (")
                    .append(it.size)
                    .append(")")
                searchAdapter.updateMasjid(it)

            }
        })

        searchModel.masjidLoadError.observe(this, Observer { isError ->
            isError?.let { listError.visibility = if (it) View.VISIBLE else View.GONE }
        })

        searchModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listError.visibility = View.GONE
                    recycler_masjids.visibility = View.GONE
                }
            }
        })
    }

    //TERKAIT GOOGLE MAPS / MARKER
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isCompassEnabled = false
        mMap.uiSettings.isZoomGesturesEnabled = true
        mMap.uiSettings.isRotateGesturesEnabled = false
        mMap.uiSettings.isZoomControlsEnabled = true
    }

    private fun observeViewMapsModel() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapsModel.masjidsList.observe(this, Observer { masjidListMap ->
            masjidListMap?.let {
                println("markerl $masjidListMap")
                recycler_masjids.visibility = View.VISIBLE


                for (element in masjidListMap) {
                    val markerOptions = MarkerOptions()
                    val googlePlace = element
                    val lat = googlePlace.latitude.toDouble()
                    val lng = googlePlace.longitude.toDouble()
                    val placeName = googlePlace.name
                    val latLng = LatLng(lat, lng)
                    println("markern $placeName")

                    markerOptions.position(latLng)
                    markerOptions.title(placeName)
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker())
                    //Add marker to map
                    mMap.addMarker(markerOptions)
                }

                //Move Camera
                val latLng = LatLng(latitude, longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))

            }
        })

        mapsModel.masjidsListError.observe(this, Observer { isError ->
            isError?.let {
                listError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        mapsModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listError.visibility = View.GONE
                    recycler_masjids.visibility = View.GONE
                }
            }
        })
    }

    private fun accessMapLiveLocation() {
        buildLocationRequest()
        buildLocationCallback()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private fun buildLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                mLastLocation = p0!!.locations.get(p0.locations.size - 1) //Get last location

                if (mMarker != null) {
                    mMarker!!.remove()
                }

                latitude = mLastLocation.latitude
                longitude = mLastLocation.longitude

                val latLng = LatLng(latitude, longitude)
                /*val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("HERE")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))

                mMarker = mMap.addMarker(markerOptions)*/


                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
                val cu = CameraUpdateFactory.newLatLngZoom(latLng, 16f)
                // Animate Camera
                mMap.animateCamera(cu)

                val url = getUrl(latitude, longitude)
                println("markerr ${getUrl(latitude, longitude)}")

                if (MapsUtils.isOnline(this@MasjidSekitarActivity)) {
                    mapsModel.fetchData(url)
                } else {
                    Toast.makeText(
                        this@MasjidSekitarActivity,
                        this@MasjidSekitarActivity.getString(R.string.you_are_offline),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 500000
        locationRequest.fastestInterval = 300000
        locationRequest.smallestDisplacement = 10f
    }

    private fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ), Constans.MY_PERMISSION_CODE
                )
            else
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ), Constans.MY_PERMISSION_CODE
                )
            return false
        } else
            return true
    }
    //END GOOGLE MAPS MARKER
    //Add back button
    override fun onSupportNavigateUp(): Boolean {
//        super.onBackPressed()
        //back button to refresh activity masjid sekitar ketika hasil filter di tampilkan
        startActivity(Intent(this@MasjidSekitarActivity, MasjidSekitarActivity::class.java))
        return true
    }

    override fun onStop() {
        myCompositeDisposable.clear()
        super.onStop()
    }
}
