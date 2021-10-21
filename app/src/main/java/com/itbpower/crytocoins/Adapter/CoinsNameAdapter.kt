package com.itbpower.crytocoins.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.itbpower.crytocoins.Fragment.CoinsFragment
import com.itbpower.crytocoins.R
import de.hdodenhof.circleimageview.CircleImageView
import java.math.RoundingMode
import java.util.*
import kotlin.collections.HashMap


class CoinsNameAdapter(
        var coinsFragment:CoinsFragment,
        var items: ArrayList<HashMap<String, String>>

) :
        RecyclerView.Adapter<CoinsNameAdapter.ViewHolder>(),Filterable {

    var countryFilterList = ArrayList<HashMap<String, String>>()

    init {
        countryFilterList = items
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsNameAdapter.ViewHolder {
        val v: View = LayoutInflater.from(parent?.context).inflate(
                R.layout.coin_name_layout,
                parent,
                false
        )
        return CoinsNameAdapter.ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var name = countryFilterList?.get(position).get("nameobject")
        holder.coin_name.text = name.toString()
        var symbol = countryFilterList?.get(position).get("Symbol")
        holder.tv_symbol.text = symbol.toString()
        var price = countryFilterList?.get(position).get("price")
        holder.market_price.text = price.toString()
        var persent_24h = countryFilterList?.get(position).get("percentage_24h")
        val value = persent_24h
        val rounded3 = value?.toBigDecimal()?.setScale(2, RoundingMode.UP)?.toDouble()
        holder.persent_24h.text ="%"+rounded3
        var image = countryFilterList.get(position).get("image")
        Glide.with(coinsFragment).load(image).placeholder(R.drawable.ic_update)
                .into(holder.coin_icon)
//        holder.main_linear.setOnClickListener{
//            coinsFragment.clicklistner(name,symbol,price,image)
//
//        }


    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var coin_name = view.findViewById<TextView>(R.id.coin_name)
        var coin_icon = view.findViewById<CircleImageView>(R.id.coin_icon)
        var market_price = view.findViewById<TextView>(R.id.market_price)
        var persent_24h = view.findViewById<TextView>(R.id.persent_24h)
        var tv_symbol = view.findViewById<TextView>(R.id.tv_symbol)
        var main_linear=view.findViewById<LinearLayout>(R.id.main_linear)

    }

    override fun getItemCount(): Int {
        return countryFilterList.size


    }


    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    countryFilterList = items
                } else {
                    val resultList = ArrayList<HashMap<String, String>>()
                    for (row in items) {
                        if (row.get("nameobject")!!.toLowerCase(Locale.ROOT)!!.contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    countryFilterList = resultList

                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults


            }

            override fun publishResults(p0: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as ArrayList<HashMap<String, String>>
                Log.d("CoinsFragment", "countryFilterList size " + countryFilterList.size)
                Log.d("CoinsFragment", "countryFilterList  " + countryFilterList)
                notifyDataSetChanged()

            }


        }
    }}


