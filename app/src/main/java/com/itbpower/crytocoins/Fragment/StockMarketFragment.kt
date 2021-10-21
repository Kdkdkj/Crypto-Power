package com.itbpower.crytocoins.Fragment

import Utils.AppUtils
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.itbpower.carwasher.view.`interface`.ApiInterface
import com.itbpower.crytocoins.Adapter.StockAdapter
import com.itbpower.crytocoins.R
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class StockMarketFragment : Fragment() {

    private val mAdSpaceName = "INTERSTITIAL_ADSPACE"
    var rv_list: RecyclerView? = null
    var stockAdapter: StockAdapter? = null
    var appUtils: AppUtils? = null
    var mContext: Context? = null
    var idlist: ArrayList<String> = ArrayList()
    val TAG="StockMarketFragment"
    var imagelist:ArrayList<HashMap<String, String>> = ArrayList()
    var mApiKey="Q4JXX748K9JPSZMMS55D"






    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_stock_market, container, false)
        initViews(view)
        appUtils = activity?.let { AppUtils(it) }
        mContext = activity
        StockHitAPi()
        return view
    }

//    private fun loadAd() {
//        var adRequest = AdRequest.Builder().build()
//        InterstitialAd.load(
//            mContext,
//            resources.getString(R.string.interstitial_ad_unit_id),
//            adRequest,
//            object : InterstitialAdLoadCallback() {
//                override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                    mInterstitialAd = interstitialAd
//                    Log.d(TAG, "interstitialAd Loaded !!")
//                    mInterstitialAd?.show(mContext as Activity)
//
//                }
//
//                override fun onAdFailedToLoad(adError: LoadAdError) {
//                    Log.d(TAG, adError?.message)
//                }
//
//
//            })
//    }


    private fun initViews(view: View) {
        rv_list = view.findViewById(R.id.rv_list)
    }

    private fun StockHitAPi() {
        appUtils?.showDialog(mContext)
        appUtils?.dialog?.show()
        val retrofit = Retrofit.Builder().baseUrl(" https://apidojo-yahoo-finance-v1.p.rapidapi.com/").addConverterFactory(GsonConverterFactory.create()).build()
        val apiInterface: ApiInterface = retrofit.create(ApiInterface::class.java)!!
        val call: Call<JsonObject> = apiInterface.getStock("INTC")
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Stock list response code : " + response.code())
                    if (response.code() == 200) {
                        appUtils?.dialog?.dismiss()
                        if (response.body() != null) {
                            val jObj = JSONObject(response.body().toString())
                            val jsonObject = jObj.getJSONObject("finance")
                            val resultArray = jsonObject.getJSONArray("result")
                            for (j in 0 until resultArray.length()) {
                                val mainobject = resultArray.getJSONObject(j)
                                val qutoArray = mainobject.getJSONArray("quotes")
                                for (i in 0 until qutoArray.length()) {
                                    val qutoObjvet = qutoArray.getJSONObject(i)
                                    val symbol = qutoObjvet.getString("symbol")
                                    val shortName = qutoObjvet.getString("shortName")
                                    val regularMarketPrice = qutoObjvet.getString("regularMarketPrice")
                                    var maplist = HashMap<String, String>()
                                    maplist.put("symbol", symbol)
                                    maplist.put("shortName", shortName)
                                    maplist.put("regularMarketPrice", regularMarketPrice)
                                    imagelist.add(maplist)
                                }

                                setAdapter()


                            }


                        }
                    }

                }
            }

            //
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("error", t.message.toString())
            }


        })
    }

    private fun setAdapter() {
        rv_list?.setLayoutManager(LinearLayoutManager(activity))
        stockAdapter = mContext?.let { StockAdapter(it, imagelist) }
        rv_list?.setAdapter(stockAdapter)
    }

//    override fun onStart() {
//        super.onStart()
//        Log.d(TAG,"fragment")
////        FlurryAgent.onStartSession(mContext!!, "Q4JXX748K9JPSZMMS55D")
//        mFlurryAdInterstitial?.fetchAd()
//    }
//
//    override fun onStop() {
//        mContext?.let { FlurryAgent.onEndSession(it) };
//        super.onStop()
//    }
//
//    override fun onDestroy() {
//        mFlurryAdInterstitial?.destroy();
//        super.onDestroy()
//    }


}



