package com.itbpower.crytocoins.Fragment

import android.content.Context
import android.content.res.TypedArray
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itbpower.crytocoins.Adapter.MoreAdapter
import com.itbpower.crytocoins.R
import java.util.*


class MoreFragment : Fragment() {
    var rv_morelist:RecyclerView?=null
    var moreAdapter:MoreAdapter?=null
    var dashboardTitle: List<String>? = null
    var tArray1: TypedArray? = null
    var mContext:Context?=null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_convert, container, false)
        rv_morelist=view.findViewById(R.id.rv_morelist)
        mContext=activity
        rv_morelist?.setLayoutManager(LinearLayoutManager(mContext))
        dashboardTitle = Arrays.asList(*this.resources.getStringArray(R.array.More_Option))
        tArray1 = resources.obtainTypedArray(R.array.more_image)
        moreAdapter = mContext?.let { MoreAdapter(it, dashboardTitle as MutableList<String>,tArray1!!) }
        rv_morelist?.setAdapter(moreAdapter)
        return view
    }






    }




