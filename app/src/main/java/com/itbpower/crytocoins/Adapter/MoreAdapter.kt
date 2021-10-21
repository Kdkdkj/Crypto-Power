package com.itbpower.crytocoins.Adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itbpower.crytocoins.Activity.Disclaimer
import com.itbpower.crytocoins.Activity.Rateus
import com.itbpower.crytocoins.Activity.SpeedTestActivity
import com.itbpower.crytocoins.Activity.Terms
import com.itbpower.crytocoins.R

class MoreAdapter(var context: Context,var titles: MutableList<String>,var tArray: TypedArray):RecyclerView.Adapter<MoreAdapter.ViewHolder>() {
    var packageName="com.itbpower.crytocoins"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreAdapter.ViewHolder {
        val v: View = LayoutInflater.from(parent?.context).inflate(
               R.layout.more_layout, parent, false   )
        return ViewHolder(v)
 }

    override fun onBindViewHolder(holder: MoreAdapter.ViewHolder, position: Int) {
        holder.more_text.setText(titles!![position])
        holder.img_more.setImageResource(tArray.getResourceId(position,0))
        holder.main_list.setOnClickListener {
            when(position){
                0->{
                    var i2 = Intent(context, Terms::class.java)
                    context?.startActivity(i2)

                }

                1->{
                    var i2 = Intent(context, Disclaimer::class.java)
                    context?.startActivity(i2)

                }
                2->{
                    var i2 = Intent(context, Rateus::class.java)
                    context?.startActivity(i2)
//                    try {
//                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
//                    } catch (e: ActivityNotFoundException) {
//                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
//                    }


                }3->{
                try {
                    val i = Intent(Intent.ACTION_SEND)
                    i.type = "text/plain"
                    i.putExtra(Intent.EXTRA_SUBJECT, "Washo")
                    var strShareMessage = "\nHey!\n" + "Welcome to Crypto Power. " + " If you want to learn everything about Crypto Coins so friends this app would be  helpful for you."+
                            "We advise you to visit this app frequently to get the latest updates. you will come to know about your investments in all crypto currencies."
                            "!!!\n"
                    strShareMessage =strShareMessage +  " \n https://play.google.com/store/apps/details?id=com.itbpower.crytocoins"
                    i.putExtra(Intent.EXTRA_TEXT, strShareMessage)
                    context.startActivity(Intent.createChooser(i, "Share via"))
                } catch (e: Exception) {
                    //e.toString();
                }

                }

                4->{
                    var i2 = Intent(context, SpeedTestActivity::class.java)
                    context?.startActivity(i2)
                }

            }
        }

    }


    override fun getItemCount(): Int {
       return titles.size
    }
    class ViewHolder(val view:View):RecyclerView.ViewHolder(view) {
        var more_text=view.findViewById<TextView>(R.id.more_text)
        var img_more=view.findViewById<ImageView>(R.id.img_more)
        var main_list=view.findViewById<LinearLayout>(R.id.main_list)

    }



}



