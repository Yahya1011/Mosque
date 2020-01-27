package com.example.mosque.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mosque.R
import com.example.mosque.adapter.HomeAdapter
import com.example.mosque.model.Mosque
import com.example.mosque.network.MosqueApi
import com.example.mosque.network.Services
import com.example.mosque.repository.MosquePagedListRepository
import com.example.mosque.utils.CustomProgressBar
import com.example.mosque.utils.EqualSpacingItemDecoration
import com.example.mosque.utils.NetworkState
import com.example.mosque.viewmodel.HomeViewModels
import kotlinx.android.synthetic.main.activity_informasi.*


class InformasiActivity : AppCompatActivity() {


    lateinit var viewModel: HomeViewModels
    lateinit var mosqueRepository: MosquePagedListRepository
    lateinit var homeAdapter : HomeAdapter
    val progressBar = CustomProgressBar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informasi)


        val apiService : MosqueApi = Services.getHomeMosque()

        mosqueRepository = MosquePagedListRepository(apiService)

        viewModel = getViewModels()

        initAdapter()

    }


    private fun getViewModels(): HomeViewModels {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModels(mosqueRepository) as T
            }

        })[HomeViewModels::class.java]
    }

    private fun initAdapter() {
        homeAdapter = HomeAdapter(this)
        rv_masjid.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_masjid.addItemDecoration(EqualSpacingItemDecoration(
            12,
            EqualSpacingItemDecoration.VERTICAL
        ))
        rv_masjid.adapter = homeAdapter
        viewModel.mosquePagedList.observe(this, Observer {
            homeAdapter.submitList(it)
        })


        viewModel.networkState.observe(this, Observer {
            //Load Progress Bar
            if (viewModel.listIsEmpty() && it == NetworkState.LOADING) {
                progressBar.show(this,"Please Wait...")
            } else {
                progressBar.dialog.dismiss()
            }

            if (!viewModel.listIsEmpty()) {
                homeAdapter.setNetworkState(it)
            }
        })


    }

}