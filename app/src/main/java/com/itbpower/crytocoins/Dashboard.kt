package com.itbpower.crytocoins

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.flurry.android.FlurryAgent
import com.flurry.android.FlurryPerformance
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.itbpower.crytocoins.Fragment.*
import kotlinx.android.synthetic.main.content_dash_board.*


class Dashboard : AppCompatActivity() {
    var mContext:Context?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(CoinsFragment())
        FlurryAgent.Builder()
                .withDataSaleOptOut(false) //CCPA - the default value is false
                .withCaptureUncaughtExceptions(true)
                .withIncludeBackgroundSessionsInMetrics(true)
                .withLogLevel(Log.VERBOSE)
                .withPerformanceMetrics(FlurryPerformance.ALL)
                .build(this, "")


        var navigationView =findViewBy
        Id<BottomNavigationView>(R.id.navigationView)
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item1 -> {
                    loadFragment(CoinsFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.item2 -> {
                    loadFragment(NewsFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.item3 -> {
                    loadFragment(StockMarketFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.item4 -> {
                    loadFragment(MoreFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false

        }
    }





    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        finish()
//        val count = supportFragmentManager.backStackEntryCount
//        if (count == 0) {
//            super.onBackPressed()
//       } else {
//            val index = supportFragmentManager.backStackEntryCount - 1
//            supportFragmentManager.popBackStack()
//            val backEntry: FragmentManager.BackStackEntry = supportFragmentManager.getBackStackEntryAt(index)
//            val stackId: Int = backEntry.getId()
//            navigationView.getMenu().getItem(stackId).setChecked(true)
//        }
    }


//    override fun onBackPressed() {
//        super.onBackPressed()
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        val  id=item.itemId
//        if(id==android.R.id.home){
//            onBackPressed()
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        inAppUpdate.onActivityResult(requestCode, resultCode, data)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        inAppUpdate.onResume()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        inAppUpdate.onDestroy()
//    }
}