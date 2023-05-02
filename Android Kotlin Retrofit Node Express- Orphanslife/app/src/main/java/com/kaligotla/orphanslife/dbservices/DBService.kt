package com.kaligotla.orphanslife.dbservices

import android.os.AsyncTask
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.io.IOException


class DBService {
    var link: URL? = null
    var method: String? = null
    var body: JSONArray? = null
    var jsonArray: JSONArray? = null

    @Throws(MalformedURLException::class)
    fun execute(providedUrl: String?, provideMethod: String?) {
        link = URL(providedUrl)
        method = provideMethod
        fromDB().execute()
    }

    @Throws(MalformedURLException::class)
    fun execute(providedUrl: String?, provideMethod: String?, providedBody: JSONArray?) {
        link = URL(providedUrl)
        method = provideMethod
        body = providedBody
        fromDB().execute()
    }

    inner class fromDB :
        AsyncTask<Void?, Void?, JSONArray>() {
        override fun onPreExecute() {
            //display progress dialog.
        }

        protected override fun doInBackground(vararg p0: Void?): JSONArray? {
            try {
                val con: HttpURLConnection = link?.openConnection() as HttpURLConnection
                con.setRequestMethod(method)
                con.setRequestProperty("User-Agent", "Mozilla/5.0")
                val responseCode: Int = con.getResponseCode()
                println("Response Code : $responseCode")
                val `in` = BufferedReader(
                    InputStreamReader(con.getInputStream())
                )
                var inputLine: String?
                val response = StringBuffer()
                while (`in`.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                `in`.close()
                val myResponse = JSONObject(response.toString())
                val data = myResponse.getJSONArray("data")
                if (myResponse.getString("status") == "success") {
                    if (data.length() > 0) {
                        jsonArray = data
                    }
                }
            } catch (e: IOException) {
                throw RuntimeException(e)
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }
            return jsonArray
        }

        override fun onPostExecute(result: JSONArray) {
            jsonArray = result
            Log.e("result", "" + result)
        }
    }

    fun gotData(): JSONArray? {
        return jsonArray
    }

}