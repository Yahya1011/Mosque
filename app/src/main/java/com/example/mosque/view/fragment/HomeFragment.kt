package com.example.mosque.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mosque.R
import com.example.mosque.common.Constans
import com.example.mosque.fragment.FasilitasFragment
import com.example.mosque.model.FasilitasString
import com.example.mosque.utils.LocationService
import com.example.mosque.utils.getServiceState
import com.example.mosque.utils.isPermissionsGranted
import com.example.mosque.viewmodel.HomeViewModels
import com.example.mosque.viewmodel.MapActivityModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_masjid_sekitar.*
import kotlinx.android.synthetic.main.map_fragment.*


class HomeFragment : Fragment() {

    companion object {

        internal val TAG = "HomeFragment"

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private var fragmentView: View? = null
    private var mapView: SupportMapFragment? = null

    //MAPS
    private lateinit var mMap: GoogleMap
    private lateinit var mLastLocation: Location
    private var mMarker: Marker? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private lateinit var mapsModel: MapActivityModel

    //Location
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    //FILTER FASILITAS
    var fasilitasList: MutableList<FasilitasString> = mutableListOf()
    private var itung: Int = 0
    private var valueSelected: Int = 0
    private var valueRespond: String = ""
    lateinit var viewModel: HomeViewModels
    private var valueFulltime : String = ""
    private var valueAc : String = ""
    private var valueCar : String = ""
    private var valueFree : String = ""
    private var valueEasy : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_mosque, container, false)
        mapView = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        return fragmentView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModels::class.java]

        mapsModel = ViewModelProvider(this).get(MapActivityModel::class.java)

        nav.setOnNavigationItemSelectedListener { p0 ->
            when (p0.itemId) {
                R.id.btn_sort -> {
                    println("SORT BELOM AKTIF")
                }
                R.id.btn_filter -> {
                    showBottomSheetDialogFragment()
                }
            }
            true
        } // BUTTOM NAVIGATION

        loadMapView() //map
//        initOnClickListener() //map
        initFilterData() // filter

    }

    private fun initFilterData() {
        val onFasilitas = this.resources.getStringArray(R.array.fasilitas)

        for (i in onFasilitas.indices) {
            fasilitasList.add(
                FasilitasString(
                    i, onFasilitas[i]
                )
            )
        }
    }

    //FILTER FASILITAS MASJID
    fun onClickEventPassData(name: String) {
        viewModel = ViewModelProvider(this)[HomeViewModels::class.java]

        if(name == "Full Time"){
            valueFulltime = "1"
        }
        submitFilterData()

        println("DATA From BottomSheet to main $name")
    }

    private fun submitFilterData() {
        if (valueSelected == 0) {

        }
            viewModel.submitFilter("", "", "", "", "")

    }

    private fun showBottomSheetDialogFragment() {
        val bottomSheetFragment = FasilitasFragment.newInstance(fasilitasList)
        bottomSheetFragment.show(requireFragmentManager(), bottomSheetFragment.tag)
    }
    //END FILTER FASILITAS

    private fun initOnClickListener() {
        expand_map.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

            }

        })
    }

    private fun loadMapView() {
        if (mapView != null) {
            mapView!!.getMapAsync(object : OnMapReadyCallback {
                override fun onMapReady(gMap: GoogleMap) {
                    gMap.isMyLocationEnabled = true
                    gMap.uiSettings.isCompassEnabled = true
                    gMap.uiSettings.isZoomGesturesEnabled = true
                    gMap.uiSettings.isZoomControlsEnabled = true
                    gMap.uiSettings.isRotateGesturesEnabled = true

                    val latLng = LatLng(-6.451590, 106.867494)
                    val markerOptions = MarkerOptions()
                        .position(latLng)
                        .title("HERE")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))

                    mMarker = gMap.addMarker(markerOptions)

                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
                    val cu = CameraUpdateFactory.newLatLngZoom(latLng, 16f)
                    // Animate Camera
                    gMap.animateCamera(cu)

                }

            })
        }
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
            isPermissionsGranted(requireContext()) -> {
                actionOnService(Constans.Actions.START)
            }

            else -> ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                Constans.LOCATION_REQUEST
            )
        }
    }

    private fun actionOnService(start: Constans.Actions) {
        if (getServiceState(requireContext()) == Constans.ServiceState.STOPPED && start == Constans.Actions.STOP) return
        Intent(requireContext(), LocationService::class.java).also {
            it.action = start.name
//            startService(it) <<-- ga kebaca startservicenya
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constans.LOCATION_REQUEST -> {
                provideLocation()
            }
        }
    }

}
