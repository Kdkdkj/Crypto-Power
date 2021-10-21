package com.itbpower.crytocoins.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.itbpower.crytocoins.R
import kotlinx.android.synthetic.main.activity_disclaimer.*

class Terms : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disclaimer)
        webView.webViewClient= WebViewClient()
        webView.loadUrl("https://cryptopower.itboulevard.com/disclaimer/")
        webView.settings.javaScriptEnabled=true
        webView.settings.setSupportZoom(true)
//

    }

    override fun onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack()
        }

            else{
            super.onBackPressed()
        }

    }

}