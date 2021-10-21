package com.itbpower.crytocoins.Activity

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class GetSpeedTestHandler: Thread() {
    var keyMap = HashMap<Int, String>()
    var valueMap = HashMap<Int, List<String>>()
    var latSelf = 0.00
    var lonSelf = 0.00
    var finished = false

    @JvmName("getKeyMap1")
    fun getKeyMap(): HashMap<Int, String> {
        return keyMap
    }

    @JvmName("getValueMap1")
    fun getValueMap(): HashMap<Int, List<String>> {
        return valueMap
    }

    @JvmName("getLatSelf1")
    fun getLatSelf(): Double {
        return latSelf
    }

    @JvmName("getLonSelf1")
    fun getLonSelf(): Double {
        return lonSelf
    }

    fun isFinished(): Boolean {
        return finished
    }

    override fun run() {
        try {
            val url = URL("https://www.speedtest.net/speedtest-config.php")
            val urlConnection = url.openConnection() as HttpURLConnection
            //            URLConnection urlConnection = url.openConnection();
//            urlConnection.connect();
            val lenghtOfFile = urlConnection.contentLength
            val code = urlConnection.responseCode
            //            int code = 200;
            if (code == 200) {
                val br = BufferedReader(
                    InputStreamReader(
                        urlConnection.inputStream
                    )
                )
                var line: String
                while (br.readLine().also { line = it } != null) {
                    if (!line.contains("isp=")) {
                        continue
                    }
                    latSelf =
                        line.split("lat=\"").toTypedArray()[1].split(" ").toTypedArray()[0].replace(
                            "\"",
                            ""
                        ).toDouble()
                    lonSelf =
                        line.split("lon=\"").toTypedArray()[1].split(" ").toTypedArray()[0].replace(
                            "\"",
                            ""
                        ).toDouble()
                    break
                }
                br.close()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return
        }

        var uploadAddress = ""
        var name = ""
        var country = ""
        var cc = ""
        var sponsor = ""
        var lat = ""
        var lon = ""
        var host = ""


        //Best server


        //Best server
        var count = 0
        try {
            val url = URL("https://www.speedtest.net/speedtest-servers-static.php")
            val urlConnection = url.openConnection() as HttpURLConnection
            val code = urlConnection.responseCode
            if (code == 200) {
                val br = BufferedReader(
                    InputStreamReader(
                        urlConnection.inputStream
                    )
                )
                var line: String
                while (br.readLine().also { line = it } != null) {
                    if (line.contains("<server url")) {
                        uploadAddress = line.split("server url=\"").toTypedArray()[1].split("\"")
                            .toTypedArray()[0]
                        lat = line.split("lat=\"").toTypedArray()[1].split("\"").toTypedArray()[0]
                        lon = line.split("lon=\"").toTypedArray()[1].split("\"").toTypedArray()[0]
                        name = line.split("name=\"").toTypedArray()[1].split("\"").toTypedArray()[0]
                        country =
                            line.split("country=\"").toTypedArray()[1].split("\"").toTypedArray()[0]
                        cc = line.split("cc=\"").toTypedArray()[1].split("\"").toTypedArray()[0]
                        sponsor =
                            line.split("sponsor=\"").toTypedArray()[1].split("\"").toTypedArray()[0]
                        host = line.split("host=\"").toTypedArray()[1].split("\"").toTypedArray()[0]
                        val ls = Arrays.asList(lat, lon, name, country, cc, sponsor, host)
                        keyMap[count] = uploadAddress
                        valueMap[count] = ls
                        count++
                    }
                }
                br.close()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        finished = true
    }
    }
