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
import com.example.mosque.view.activity.DonasiActivity
import com.example.mosque.view.activity.Informasi1Activity
import com.example.mosque.view.activity.LaporanActivity
import com.example.mosque.view.activity.MasjidSekitarActivity
import com.example.mosque.viewmodel.HomeViewModels
import kotlinx.android.synthetic.main.activity_informasi.*
import kotlinx.android.synthetic.main.row.*

class InformasiActivity : AppCompatActivity() {

    lateinit var viewModel: HomeViewModels
    private val homeAdapter = HomeAdapter(ArrayList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informasi)

//        donasi1.setOnClickListener{
//            val intent = Intent (this, DonasiActivity::class.java)
//            startActivity(intent)
//        }


        viewModel = ViewModelProvider(this)[HomeViewModels::class.java]
        viewModel.refresh()


        rv_masjid.apply{
            layoutManager = LinearLayoutManager(context)
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
                println("DATA ${it.size}")
                rv_masjid.visibility = View.VISIBLE
                homeAdapter.updateHome(it)
                homeAdapter.setOnItemClickListener(object : HomeAdapter.OnItemClickListener{
                    override fun onItemSelected(masjidItems: Mosque) {
                        setOnClickItem(masjidItems.mosqueId)
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

    private fun setOnClickItem(mosqueId: Int){
        println("$mosqueId")
        val intent = Intent(this, LaporanActivity::class.java)
        intent.putExtra("key", mosqueId)
        startActivity(intent)
    }

}