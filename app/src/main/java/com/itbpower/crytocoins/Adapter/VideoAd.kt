package com.itbpower.crytocoins.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.itbpower.crytocoins.Fragment.NewsFragment
import com.itbpower.crytocoins.Pojo.DanceArray
import com.itbpower.crytocoins.R
import kotlin.collections.ArrayList

class VideoAd(var newsFragment: NewsFragment, var DanceArraylist: ArrayList<DanceArray>, var context: Context):
        RecyclerView.Adapter<VideoAd.ViewHolder>() {

    private var clickOnInterface: VideoAd.itemClickListner? = null
    var YOUTUBE_IMAGE_FRONT = "http://img.youtube.com/vi/"
    var YOUTUBE_IMAGE_BACK = "/hqdefault.jpg"



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAd.ViewHolder {
        val v: View = LayoutInflater.from(parent?.context)
                .inflate(R.layout.bitcoin_list, parent, false)
        return VideoAd.ViewHolder(v)

    }

    override fun onBindViewHolder(holder: VideoAd.ViewHolder, position: Int) {
        holder.tv_title.setText(DanceArraylist.get(position).getTitle())
        Glide.with(context).load(YOUTUBE_IMAGE_FRONT + DanceArraylist.get(position).getVideoID() + YOUTUBE_IMAGE_BACK).placeholder(R.drawable.ic_launcher_background).into(holder.iv_image)
        holder.ll_layout.setOnClickListener { clickOnInterface?.listeners(position) }

    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var tv_title = view.findViewById<TextView>(R.id.tv_title)
        var iv_image = view.findViewById<ImageView>(R.id.iv_image)
        var ll_layout = view.findViewById<LinearLayout>(R.id.ll_layout)

    }

    override fun getItemCount(): Int {
        return DanceArraylist.size
    }

    interface itemClickListner {
        fun listeners(position: Int)
    }

    fun setClickOnInterface(clickOnInterface: itemClickListner?) {
        this.clickOnInterface = clickOnInterface
    }


}