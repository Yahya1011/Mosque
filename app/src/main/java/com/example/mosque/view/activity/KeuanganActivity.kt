package com.example.mosque.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mosque.R
import com.example.mosque.view.LaporanActivity

class KeuanganActivity : AppCompatActivity() {

    lateinit var button5: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keuangan)

        // Initializing a String Array
        val jenis = arrayOf<String>("Pemasukan","Pengeluaran")
        val kategori = arrayOf<String>("Infaq","Zakat","Wakaf","Capex","Opex")

        val Spinner1 = findViewById(R.id.spinner) as Spinner
        val Spinner2 = findViewById(R.id.spinner1) as Spinner


        var adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,jenis)
        var adapter1= ArrayAdapter(this,android.R.layout.simple_list_item_1,kategori)
        Spinner1.adapter=adapter
        Spinner2.adapter=adapter1

        //LISTENER
        Spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
    }
}