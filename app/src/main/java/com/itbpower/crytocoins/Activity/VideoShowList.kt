package com.itbpower.crytocoins.Activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.itbpower.crytocoins.Adapter.VideoAd
import com.itbpower.crytocoins.Pojo.DanceArray
import com.itbpower.crytocoins.R
import java.util.*

class VideoShowList : AppCompatActivity() {
    var rv_list: RecyclerView? = null
    var Rl_toobar: RelativeLayout? = null
    var iv_back: ImageView? = null
    var tv_toolbar: TextView? = null
    var videoAdapter:VideoAd?=null
  private  var  danceArrayList= ArrayList<DanceArray>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_show_list)
//        initViews()
//        getIntentData()
    }

//    private fun getIntentData() {
//        danceArrayList = intent.getSerializableExtra("danceArray") as ArrayList<DanceArray>
//        title = intent.getStringExtra("title")
//        tv_toolbar!!.text = title
//        setAdapter()
//
//    }


//    private fun setAdapter() {
//        rv_list!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        videoAdapter = VideoAd(this, danceArrayList)
//        rv_list!!.adapter = videoAdapter
//        videoAdapter!!.setClickOnInterface(object : VideoAdapter.itemClickListner {
//         override   fun listeners(position: Int) {
//                startActivity(
//                    Intent(this@VideoShowList, YouTubeScreen::class.java)
//                        .putExtra("YoutubeLink", danceArrayList[position].videoID)
//                )
//            }
//        })
//    }

//    private fun initViews() {
//        danceArrayList = ArrayList<DanceArray>()
//        Rl_toobar = findViewById<RelativeLayout>(R.id.Rl_toobar)
//        iv_back = findViewById<ImageView>(R.id.iv_back)
//        tv_toolbar = findViewById<TextView>(R.id.tv_toolbar)
//        rv_list = findViewById<RecyclerView>(R.id.rv_list)
//
//    }
}