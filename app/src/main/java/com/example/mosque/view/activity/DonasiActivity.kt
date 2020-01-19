package com.example.mosque.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mosque.R
import kotlinx.android.synthetic.main.activity_donasi.*

class DonasiActivity : AppCompatActivity() {

//    lateinit var button1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donasi)

        donasi.setOnClickListener {
            val intent = Intent(this, DetailDonasiActivity::class.java)
            startActivity(intent)
        }

//Radio button on click change

        grupRadio.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener{
                    group, checkId ->

                val radio_langange: RadioButton = this.findViewById(checkId)


                Toast.makeText(applicationContext," On Checked change :${radio_langange.text}",Toast.LENGTH_SHORT).show()


            }
        )
        radio_group.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener{
                    group, checkedId ->

                val radio_langange: RadioButton = findViewById(checkedId)


                Toast.makeText(applicationContext," On Checked change :${radio_langange.text}",Toast.LENGTH_SHORT).show()

            }
        )


        // Get radio group selected status and text using button click event
        radio_group.setOnClickListener{
            // Get the checked radio button id from radio group
            var id: Int = radio_group.checkedRadioButtonId
            if (id!=-1){ // If any radio button checked from radio group
                // Get the instance of radio button using id
                val radio:RadioButton = findViewById(id)
                Toast.makeText(applicationContext,"On button click : ${radio.text}",
                    Toast.LENGTH_SHORT).show()
            }else{
                // If no radio button checked in this radio group
                Toast.makeText(applicationContext,"On button click : nothing selected",
                    Toast.LENGTH_SHORT).show()
            }
        }
        grupRadio.setOnClickListener{
            // Get the checked radio button id from radio group
            var id: Int = grupRadio.checkedRadioButtonId
            if (id!=-1){ // If any radio button checked from radio group
                // Get the instance of radio button using id
                val radio1:RadioButton = findViewById(id)
                Toast.makeText(applicationContext,"On button click : ${radio1.text}",
                    Toast.LENGTH_SHORT).show()
            }else{
                // If no radio button checked in this radio group
                Toast.makeText(applicationContext,"On button click : nothing selected",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Get the selected radio button text using radio button on click listener
    fun radio_button_click(view: View){
        // Get the clicked radio button instance
        val radio: RadioButton = findViewById(radio_group.checkedRadioButtonId)
        Toast.makeText(applicationContext,"On click : ${radio.text}",
            Toast.LENGTH_SHORT).show()
        val radio1: RadioButton = findViewById(radio_group.checkedRadioButtonId)
        Toast.makeText(applicationContext,"On click : ${radio1.text}",
            Toast.LENGTH_SHORT).show()
    }
}