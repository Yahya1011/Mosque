package com.example.mosque.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mosque.R
import com.example.mosque.adapter.HomeAdapter
import com.example.mosque.model.Mosque
import com.example.mosque.utils.EqualSpacingItemDecoration
import com.example.mosque.viewmodel.HomeViewModels
import kotlinx.android.synthetic.main.activity_informasi.*

class InformasiActivity : AppCompatActivity() {

    lateinit var viewModel: HomeViewModels
    private val homeAdapter = HomeAdapter(ArrayList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informasi)


        viewModel = ViewModelProvider(this)[HomeViewModels::class.java]
        viewModel.refresh()


        rv_masjid.apply{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(EqualSpacingItemDecoration(
                12,
                EqualSpacingItemDecoration.HORIZONTAL
            ))

            adapter = homeAdapter
        }


        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            viewModel.refresh()
        }
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.mosquesData.observe(this, Observer{ mosques ->
            mosques?.let {
                println("DATA INFORMASI ${it.size}")
                rv_masjid.visibility = View.VISIBLE
                homeAdapter.updateHome(it)
                homeAdapter.setOnInfoItemClickListener(object : HomeAdapter.OnInfoItemClickListener{
                    override fun onItemSelected(masjidItems: Mosque) {
                        setOnClickItemInfo(masjidItems.mosqueId)

                    }
                })

                homeAdapter.setOnDonasiItemClickListener(object : HomeAdapter.OnDonasiItemClickListener{
                    override fun onItemSelected(masjidItems: Mosque) {
                        setOnClickItemDonate(masjidItems.mosqueId)
                    }

                })
            }
        })

        viewModel.masjidLoadError.observe(this, Observer { isError->
            isError?.let { tv_error.visibility = if(it) View.VISIBLE else View.GONE}
        })

        viewModel.loading.observe(this, Observer { isLoading->
            isLoading?.let {
                progressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    tv_error.visibility = View.GONE
                    rv_masjid.visibility = View.GONE
                }
            }
        })

    }

    private fun setOnClickItemInfo(mosqueId: Int){
        println("Laporan $mosqueId")
        val intent = Intent(this, LaporanActivity::class.java)
        intent.putExtra("key", mosqueId)
        startActivity(intent)
    }

    private fun setOnClickItemDonate(mosqueId: Int){
        println("Donasi $mosqueId")
        val intent = Intent(this, DonasiActivity::class.java)
        intent.putExtra("key", mosqueId)
        startActivity(intent)
    }

}