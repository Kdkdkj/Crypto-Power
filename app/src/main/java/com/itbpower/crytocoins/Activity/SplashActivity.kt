package com.itbpower.crytocoins.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import com.itbpower.crytocoins.Dashboard
import com.itbpower.crytocoins.Database.UserPreferences
import com.itbpower.crytocoins.R

class SplashActivity : AppCompatActivity() {
    var userPreferences: UserPreferences?=null
    var currency =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN

        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            finish()
        }, 3000) //
        userPreferences= UserPreferences(this@SplashActivity)
        Log.d("valuesplashscrren",currency.toString())
        currency=userPreferences?.getUserCurrency().toString()

    }
    }
