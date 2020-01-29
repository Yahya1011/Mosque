package com.example.mosque.view

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.mosque.R
import com.example.mosque.adapter.KeuanganAdapter
import com.example.mosque.adapter.LaporanAdapter
import com.example.mosque.helper.AppPreferencesHelper
import com.example.mosque.model.FinanceModel
import com.example.mosque.model.MosqueFinance
import com.example.mosque.viewmodel.KeuanganViewModel
import com.example.mosque.viewmodel.LaporanViewModel
import kotlinx.android.synthetic.main.activity_keuangan.*

class KeuanganActivity() : AppCompatActivity() {

    /*lateinit var mPrefData: AppPreferencesHelper
    private val isLogin: Boolean = false
    var valueId: Int = 0
    lateinit var keuanganViewModel: LaporanViewModel
    private val laporanAdapter = LaporanAdapter(ArrayList())

    var progressDrawable: CircularProgressDrawable? = null
    var imgTarget: String = ""*/

    lateinit var button5: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keuangan)

        /*val recyclerDataAdapter = KeuanganAdapter(getDummyDataToPass())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerDataAdapter
        recyclerView.setHasFixedSize(true)*/

        /*mPrefData = AppPreferencesHelper(this)
        println(mPrefData.isLoginIn())

        keuanganViewModel = ViewModelProvider(this)[keuanganViewModel::class.java]
        keuanganViewModel.refreshLaporan(valueId)

        rv_keuangan.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = laporanAdapter
        }*/

        /*reciveData()*/

        // Initializing a String Array
        /*val jenis = arrayOf<String>("Pemasukan","Pengeluaran")
        val kategori = arrayOf<String>("Infaq","Zakat","Wakaf","Capex","Opex")

        val Spinner1 = findViewById(R.id.spinner) as Spinner
        val Spinner2 = findViewById(R.id.spinner1) as Spinner


        var adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,jenis)
        var adapter1= ArrayAdapter(this,android.R.layout.simple_list_item_1,kategori)
        Spinner1.adapter=adapter
        Spinner2.adapter=adapter1*/

        //LISTENER
        /*Spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                Toast.makeText(this@KeuanganActivity, jenis[i], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {
            }
        }
        Spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                Toast.makeText(this@KeuanganActivity, kategori[i], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {
            }
        }

        button5 = findViewById<Button>(R.id.keuangan)

        button5.setOnClickListener{
            val intent = Intent(this, LaporanActivity::class.java)
            startActivity(intent)
        }
    }*/

        /*private fun reciveData() : Int {
        val extras = intent.extras
        when {
            extras != null -> {
                valueId = extras.getInt("key")
                //The key argument here must match that used in the other activity
            }
        }

        return valueId

    }*/
    }
}

/*private fun getDummyDataToPass(): MutableList<FinanceModel> {
println("")
val dummyDataItems: MutableList<MosqueFinance> = ArrayList()
var childDataItems: MutableList<FinanceModel>

for (k in 1..4) {
    childDataItems = ArrayList()
    for (i in 0..k) {
        val dummyChildDataItem = FinanceModel("Child Item $k.$i")
        childDataItems.add(dummyChildDataItem)
    }
    val dummyParentDataItem = MosqueFinance("Parent $k", childDataItems)
    dummyDataItems.add(dummyParentDataItem)
}
return dummyDataItems
}
}*/
