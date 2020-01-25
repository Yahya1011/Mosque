package com.example.mosque.view

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.mosque.R
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    lateinit var animationDrawable: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialView()
    }

    private fun initialView() {
        animationDrawable.apply {
            layout_login.background
        }
        animationDrawable.setEnterFadeDuration(5000)
        animationDrawable.setExitFadeDuration(2000)


    }

//    override fun onPause() {
//        super.onPause()
//        if (animationDrawable != null && animationDrawable.isRunning) {
//            animationDrawable.stop()
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if (animationDrawable != null && !animationDrawable.isRunning) {
//            animationDrawable.start()
//        }
//    }

    fun validate(): Boolean {
        var valid = true
        val email: String = input_email.getText().toString()
        val password: String = input_pass.getText().toString()
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.setError("enter a valid email address")
            valid = false
        } else {
            input_email.setError(null)
        }
        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            input_pass.setError("between 4 and 10 alphanumeric characters")
            valid = false
        } else {
            input_pass.setError(null)
        }
        return valid
    }

}