package com.itbpower.crytocoins.Activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.itbpower.crytocoins.Fragment.CoinsFragment
import com.itbpower.crytocoins.R
import de.hdodenhof.circleimageview.CircleImageView


class CoinsActivity : AppCompatActivity(), View.OnClickListener {
    var coin_name:TextView?=null
    var tv_symbol:TextView?=null
    var cv_image:CircleImageView?=null
    var name:String?=null
     var Symbol:String?=null
     var image:String?=null
     var toolbar_back:ImageView?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coins)
        name = intent.getStringExtra("Name")
        Symbol=intent.getStringExtra("Symbol")
        val bitmap = this.intent.getParcelableExtra<Parcelable>("bitmap") as Bitmap?
        cv_image= findViewById(R.id.cv_image)
        cv_image?.setImageBitmap(bitmap)
        toolbar_back?.setOnClickListener(this)

        initviews()
    }

    private fun initviews() {
        coin_name= findViewById(R.id.coin_name)
        tv_symbol= findViewById(R.id.tv_symbol)
        coin_name?.setText(name)
        tv_symbol?.setText(Symbol)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.toolbar_back -> {
                val intent = Intent(this, CoinsFragment::class.java)
                startActivity(intent)
            }

        }

    }
}