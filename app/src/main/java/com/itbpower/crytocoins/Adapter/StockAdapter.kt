package com.itbpower.crytocoins.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itbpower.crytocoins.R
import java.util.HashMap

class StockAdapter(var context: Context, var items: ArrayList<HashMap<String, String>>) :
        RecyclerView.Adapter<StockAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockAdapter.ViewHolder {
        val v: View = LayoutInflater.from(parent?.context).inflate(R.layout.name_layout, parent, false)
        return StockAdapter.ViewHolder(v)

    }


    override fun onBindViewHolder(holder: StockAdapter.ViewHolder, position: Int) {
        var name =items?.get(position).get("symbol")
        holder.symbol.text= name.toString()
        var shortName =items?.get(position).get("shortName")
        holder.company.text= shortName.toString()
        var regularMarketPrice =items?.get(position).get("regularMarketPrice")
        holder.price.text= regularMarketPrice.toString()


    }


    override fun getItemCount(): Int {
        return items.size

    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var symbol = view.findViewById<TextView>(R.id.symbol)
        var price = view.findViewById<TextView>(R.id.price)
        var company = view.findViewById<TextView>(R.id.company)
        var market_persent = view.findViewById<TextView>(R.id.market_persent)








    }
}
