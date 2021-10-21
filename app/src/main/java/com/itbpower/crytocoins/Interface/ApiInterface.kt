package com.itbpower.carwasher.view.`interface`


import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiInterface {

    @GET("api/v3/coins/markets")
    fun getCoins(
            @Query("vs_currency") vs_currency:String ):Call<JsonArray>


        @Headers("x-rapidapi-key:0d61e4e12emsh38a9cd97311b77bp1189c6jsn1082bf621d9d")
        @GET("stock/v2/get-recommendations")
        fun getStock(
                @Query("symbol") symbol:String ):Call<JsonObject>

    }

