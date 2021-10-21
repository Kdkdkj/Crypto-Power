package com.itbpower.crytocoins.Activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itbpower.crytocoins.Database.UserPreferences
import com.itbpower.crytocoins.R
import kotlinx.android.synthetic.main.activity_rateus.*
import java.util.*

class Rateus : AppCompatActivity(), View.OnClickListener {
    var context:Context?=null
    var userPreferences:UserPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rateus)
        context = this
        userPreferences = this?.let { UserPreferences(it) }
        btn_rateus.requestFocus()
        btn_rateus.requestFocusFromTouch()
        btn_rateus.setOnClickListener(this)
        btn_later.setOnClickListener(this)


    }





    override fun onResume() {
        super.onResume()
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_rateus -> {
                try {
                    val appPackageName = applicationContext.packageName
                    val playstoreIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                    context?.let { userPreferences?.setRateUsDontaskagain(true, it) }
                    startActivity(playstoreIntent)
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        resources.getString(R.string.device_not_supported),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            R.id.btn_later -> {
                context?.let { userPreferences?.setRateUsCount(0, it) }
                onBackPressed()
            }

        }

    }

}