package com.itbpower.crytocoins.Fragment

import Utils.AppUtils
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.itbpower.crytocoins.Activity.YouTubeScreen
import com.itbpower.crytocoins.Adapter.NewsAdapter
import com.itbpower.crytocoins.Adapter.VideoAd
import com.itbpower.crytocoins.Pojo.DanceArray
import com.itbpower.crytocoins.Pojo.MainModel
import com.itbpower.crytocoins.R
import org.json.JSONException
import org.json.JSONObject

class NewsFragment : Fragment() {
    var rv_newslist: RecyclerView? = null
    var newsAdapter: NewsAdapter?=null
    var progress: Dialog? = null
    var storageReference: StorageReference? = null
    var storage: FirebaseStorage? = null
    var database: FirebaseDatabase? = null
    var reference: DatabaseReference? = null
    var  imagesUploadList= ArrayList<String>()
    var videoAdapter: VideoAd?=null
    var appUtils:AppUtils?=null
    var mContext:Context?=null
    val danceArrayArrayList= ArrayList<DanceArray>()
//    lateinit var mAdView : AdView






    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_news, container, false)
        initViews(view)
        mContext=activity
        appUtils = activity?.let { AppUtils(it) }
//        MobileAds.initialize(this) {}
//        mAdView = view.findViewById(R.id.adView)
//        val adRequest = AdRequest.Builder().build()
//        mAdView.loadAd(adRequest)
        prepareFirebaseDatabase()
        getDataFromFirebase(view)
        return view
    }


    private fun getDataFromFirebase(view: View) {
        appUtils?.showDialog(mContext)
        appUtils?.dialog?.show()
        reference!!.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("CheckResult")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val stringMap = dataSnapshot.value as Map<String, String>?
                setData(stringMap)
                appUtils?.dialog?.dismiss()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, databaseError.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setData(stringMap: Map<String, String>?) {
        try {
            val mainobject = JSONObject(stringMap)
            val danceobject = mainobject.getJSONObject("bitcoin")
                val mainModel = MainModel()
                val danceArray = `danceobject`.getJSONArray("bitArray")
                for (j in 0 until danceArray.length()) {
                    val arrayobject = danceArray.getJSONObject(j)
                    val danceArray1 = DanceArray()
                    danceArray1.setTitle(arrayobject.getString("Title"))
                    danceArray1.setVideoID(arrayobject.getString("videoId"))
                    danceArrayArrayList.add(danceArray1)
                }
            if(danceArrayArrayList.size>0){
                setAdapter()
            }

            }
         catch (e: JSONException) {
            e.printStackTrace()
        }



    }
    private fun setAdapter() {
        videoAdapter = VideoAd(this@NewsFragment, danceArrayArrayList,mContext!!)
        rv_newslist?.setLayoutManager(LinearLayoutManager(context))
        rv_newslist?.setAdapter(videoAdapter)
        videoAdapter!!.setClickOnInterface(object : VideoAd.itemClickListner {
            override fun listeners(position: Int) {
                startActivity(
                        Intent(context, YouTubeScreen::class.java)
                                .putExtra("YoutubeLink", danceArrayArrayList[position].videoID)
                )
            }
        })

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    private fun prepareFirebaseDatabase() {
        database = FirebaseDatabase.getInstance()
        reference = database!!.getReference("data")
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

    }
    private fun initViews(view: View) {
        progress = Dialog(view.context)
        rv_newslist = view.findViewById(R.id.rv_newslist)

//        rv_newslist?.setLayoutManager(LinearLayoutManager(activity))
//        newsAdapter = NewsAdapter(this@NewsFragment)
//        rv_newslist?.setAdapter(newsAdapter)

    }




}



