package com.example.mosque.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            startActivity(
                Intent(this@SplashScreen,
                    MainActivity::class.java)
            )
        }, 3000) //3s
    }
}
