package com.itbpower.crytocoins.Adapter

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itbpower.crytocoins.R

class CutrrencyAdapter(var context: Context, var titles: MutableList<String>, var shortName:MutableList<String>, var tArray: TypedArray


                       ) :
RecyclerView.Adapter<CutrrencyAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CutrrencyAdapter.ViewHolder {
        val v: View = LayoutInflater.from(parent?.context).inflate(
            R.layout.popup,
            parent,
            false
        )
        return CutrrencyAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_currency.setText(titles!![position])
        holder.shortname.setText(shortName!![position])
        holder.cv_list.setImageResource(tArray!!.getResourceId(position, 0))
        holder.rv_list.setOnClickListener{
            val intent = Intent().apply {
                action = "BROADCAST_ACTION"
                putExtra("item", shortName!![position])
            }
            Log.d("CoinsFragment","inside adapter Onclick")
            it.context.sendBroadcast(intent)
//            LocalBroadcastManager.getInstance(it.context).sendBroadcast(intent)


        }




    }



    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var tv_currency=view.findViewById<TextView>(R.id.tv_currency)
        var cv_list=view.findViewById<ImageView>(R.id.cv_list)
        var shortname=view.findViewById<TextView>(R.id.shortname)
        var rv_list=view.findViewById<RelativeLayout>(R.id.rv_list)



    }

    override fun getItemCount(): Int {
        return titles!!.size

    }
}