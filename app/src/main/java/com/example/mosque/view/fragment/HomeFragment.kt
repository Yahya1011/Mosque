package com.example.mosque.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mosque.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.map_fragment.*


class HomeFragment : Fragment() {

    companion object {

        internal val TAG = "HomeFragment"

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }

    }

    private var fragmentView: View? = null
    private var mapView : SupportMapFragment?  = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_mosque, container, false)
        mapView = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        return fragmentView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadMapView()
        initOnClickListener()

    }

    private fun loadMapView() {
        if (mapView != null){
            mapView!!.getMapAsync(object : OnMapReadyCallback{
                override fun onMapReady(gMap: GoogleMap) {
                    gMap.isMyLocationEnabled = true
                    gMap.uiSettings.isCompassEnabled = false
                    gMap.uiSettings.isZoomGesturesEnabled = true
                    gMap.uiSettings.isRotateGesturesEnabled = false
                    gMap.uiSettings.isZoomControlsEnabled = true
                }

            })
        }
    }

    private fun initOnClickListener() {
        expand_map.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                map.
            }

        })
    }
}
