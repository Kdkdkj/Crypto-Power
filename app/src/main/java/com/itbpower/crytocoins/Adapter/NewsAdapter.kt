package com.itbpower.crytocoins.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itbpower.crytocoins.Fragment.NewsFragment
import com.itbpower.crytocoins.Pojo.MainModel
import com.itbpower.crytocoins.R
class NewsAdapter(var newsFragment: NewsFragment,var mainModelList:List<MainModel>,var context:Context
                  ) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private var clickOnInterface: NewsAdapter.itemClickListner?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val v: View = LayoutInflater.from(parent?.context)
            .inflate(R.layout.news_layout, parent, false)
        return NewsAdapter.ViewHolder(v)

    }


    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
//        holder.tv_conis_name.setText(mainModelList.get(position).getName())
//        Glide.with(context).load(mainModelList[position].image_path)
//            .placeholder(R.drawable.ic_launcher_background).into(holder.coins_image)
        holder.main_linear.setOnClickListener(View.OnClickListener {
            clickOnInterface!!.listeners(
                position
            )
        })

    }


    override fun getItemCount(): Int {
        return mainModelList.size

    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var main_linear = view.findViewById<LinearLayout>(R.id.main_linear)
        var tv_conis_name = view.findViewById<TextView>(R.id.tv_conis_name)
        var coins_image = view.findViewById<ImageView>(R.id.coins_image)


    }
    interface itemClickListner {
        fun listeners(position: Int)
    }

    fun setClickOnInterface(clickOnInterface: itemClickListner?) {
        this.clickOnInterface = clickOnInterface
    }
}