package com.itbpower.crytocoins.Fragment

import Utils.AppUtils
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.itbpower.carwasher.view.`interface`.ApiInterface
import com.itbpower.crytocoins.Activity.CoinsActivity
import com.itbpower.crytocoins.Adapter.CoinsNameAdapter
import com.itbpower.crytocoins.Adapter.CutrrencyAdapter
import com.itbpower.crytocoins.Database.UserPreferences
import com.itbpower.crytocoins.R
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap



private const val UPDATE_REQUEST_CODE = 123



class CoinsFragment : Fragment(), View.OnClickListener {

    private lateinit var broadcastReceiver: BroadcastReceiver
    var rv_list: RecyclerView? = null
    var rv_currency: RecyclerView? = null
    var coinsNameAdapter: CoinsNameAdapter? = null
    var mContext: Context? = null
    var appUtils: AppUtils? = null
    var id_flag = 0
    var Ref: Button?=null
    var imagelist: ArrayList<HashMap<String, String>> = ArrayList()
    val TAG = "CoinsFragment"
    var image_popup: ImageView? = null
    var Cancle: TextView? = null
    var adapter: CutrrencyAdapter? = null
    var dashboardTitle: List<String>? = null
    var shortName: List<String>? = null
    var tArray1: TypedArray? = null
    var countryname: TextView? = null
    var currency: String = ""
    var currencyValue = ""
    var tv_noRecordfound: TextView? = null
    var userPreferences: UserPreferences? = null
    var txt_price: TextView? = null
    lateinit var mAlertDialog: AlertDialog


//    private lateinit var inAppUpdate:InAppUpdate




    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_coins, container, false)
        initViews(view)
        appUtils = activity?.let { AppUtils(it) }
        userPreferences = activity?.let { UserPreferences(it) }
        mContext = activity
//            MobileAds.initialize(mContext)
//            loadAd()

            setHasOptionsMenu(true)
            val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
            (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
            (activity as? AppCompatActivity)?.supportActionBar?.show()
            Log.d(TAG, "current Currency :" + userPreferences?.getUserCurrency())
         (
                    userPreferences?.getUserCurrency()
            )
            image_popup?.setOnClickListener(this)
            txt_price?.setOnClickListener(this)
            countryname?.setOnClickListener(this)
            Ref?.setOnClickListener(this)
            return view

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == "BROADCAST_ACTION"
                ) {
                    mAlertDialog.dismiss()
                    Log.d(
                            "CoinsFragment",
                            "inside Broadcast Receiver" + "value " + intent?.extras?.get(
                                    "item"
                            ).toString()
                    )
                    currency = intent?.extras?.get("item").toString()
                    countryname?.text = "( " + currency.toString() + " )"
                    userPreferences?.saveCurrency(currency)
                    newOrderHitAPi(currency as String?)
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode== UPDATE_REQUEST_CODE && resultCode != RESULT_OK){
            appUtils?.ShowMessage(mContext, "Cancel")
        }
        super.onActivityResult(requestCode, resultCode, data)
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

    private fun setAdapter(imagelist: ArrayList<HashMap<String, String>>) {
        rv_list?.setLayoutManager(LinearLayoutManager(mContext))
        coinsNameAdapter = CoinsNameAdapter(this, imagelist)
        rv_list?.setAdapter(coinsNameAdapter)

    }

    private fun newOrderHitAPi(currency: String?) {
        if (imagelist.size > 0) {
            imagelist.clear()
        }
        if (currency?.isEmpty() == true || currency.isNullOrEmpty() || currency.isEmpty()) {
            currencyValue = "USD"
        } else {
            currencyValue = currency
        }
        Log.d(TAG, "currencyValue" + currencyValue.toString())
        appUtils?.showDialog(mContext)
        appUtils?.dialog?.show()
        val retrofit = Retrofit.Builder().baseUrl("https://api.coingecko.com/").addConverterFactory(
                GsonConverterFactory.create()
        ).build()
        val apiInterface: ApiInterface = retrofit?.create(ApiInterface::class.java)!!
        val call: Call<JsonArray> = apiInterface.getCoins(currencyValue)!!
        call.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Id list response code : " + response.code())
                    if (response.code() == 200
                    ) {
                        appUtils?.dialog?.dismiss()
                        if (response.body() != null) {
                            val jsonArray = JSONArray(response.body().toString())
                            for (i in 0 until jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(i)
                                val nameobject = jsonObject.getString("name")
                                val image = jsonObject.getString("image")
                                val price = jsonObject.getString("current_price")
                                val Symbol = jsonObject.getString("symbol")
                                val percentage_24h =
                                        jsonObject.getString("price_change_percentage_24h")
                                var maplist = HashMap<String, String>()
                                maplist.put("nameobject", nameobject)
                                maplist.put("image", image)
                                maplist.put("price", price)
                                maplist.put("percentage_24h", percentage_24h)
                                maplist.put("Symbol", Symbol)
                                imagelist.add(maplist)
                                Log.d(TAG, "pricelist " + imagelist.toString())
                            }
                            setAdapter(imagelist)

                        }

                    }
                }

            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("error", t.message.toString())
            }

        })
    }

    private fun initViews(view: View) {
        rv_list = view.findViewById(R.id.rv_list)
        image_popup = view.findViewById(R.id.image_popup)
        countryname = view.findViewById(R.id.countryname)
        txt_price = view.findViewById(R.id.txt_price)
        Ref=   view.findViewById(R.id.Ref)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.image_popup, R.id.countryname, R.id.txt_price -> {
                openCurrency()
            }

            R.id.Ref -> {
//                if (mInterstitialAd != null) {
//                    mInterstitialAd?.show(mContext as Activity)
////
//                    mInterstitialAd?.fullScreenContentCallback =
//                        object : FullScreenContentCallback() {
//                            override fun onAdDismissedFullScreenContent() {
//                                Log.d(TAG, "Ad was dismissed.")
//                                mInterstitialAd = null
//                                newOrderHitAPi(userPreferences?.getUserCurrency())
//                                loadAd()
////
//                            }
//
//                            //
//                            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
//                                Log.d(TAG, "Ad failed to show.")
//                                mInterstitialAd = null
//                                loadAd()
//
////
//                            }
//
//                            override fun onAdShowedFullScreenContent() {
//                                Log.d(TAG, "Ad showed fullscreen content.")
//
//                            }
//
//
//                        }
//
//                } else {
//                    loadAd()
//                }

            }

        }

    }


    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume call")
        activity?.registerReceiver(broadcastReceiver, IntentFilter("BROADCAST_ACTION"))
        activity?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

    }


    override fun onPause() {
        activity?.unregisterReceiver(broadcastReceiver)
        super.onPause()
    }

    private fun openCurrency() {
        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.curreny, null)
        val mBuilder = AlertDialog.Builder(activity)
                .setView(mDialogView)
        mAlertDialog = mBuilder.show()
        rv_currency = mAlertDialog.findViewById<RecyclerView>(R.id.rv_currency)
        Cancle = mAlertDialog.findViewById<TextView>(R.id.Cancle)
        rv_currency?.setLayoutManager(
                LinearLayoutManager(
                        mContext,
                        LinearLayoutManager.VERTICAL,
                        false
                )
        )
        dashboardTitle = Arrays.asList(*this.resources.getStringArray(R.array.Currency_name))
        shortName = Arrays.asList(*this.resources.getStringArray(R.array.Short_name))
        tArray1 = resources.obtainTypedArray(R.array.flag_Images)
        adapter = context?.let {
            CutrrencyAdapter(
                    it, dashboardTitle as MutableList<String>, shortName as MutableList<String>,
                    tArray1!!
            )
        }
        rv_currency?.setAdapter(adapter)
        Cancle?.setOnClickListener {
            mAlertDialog.dismiss()

        }

    }

    fun clicklistner(name: String?, symbol: String?, price: String?, image: String?) {
        var bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon)
        val intent = Intent(activity, CoinsActivity::class.java)
        intent.putExtra("Name", name)
        intent.putExtra("Symbol", symbol)
        intent.putExtra("price", price)
        intent.putExtra("bitmap", bitmap)
        startActivity(intent)


    }



}






