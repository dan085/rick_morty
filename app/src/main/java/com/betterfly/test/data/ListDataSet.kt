package com.betterfly.test.data

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.betterfly.test.ui.model.Item
import com.betterfly.test.domain.ApiRest
import com.betterfly.test.domain.CustomVolleyRequestQueue
import com.betterfly.test.ui.model.itemOrigin
import com.betterfly.test.ui.model.location
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class ListDataSet {
    private var futureRequest = RequestFuture.newFuture<JSONObject>()
    fun getListItems(context:Context,PAGE:Int): ArrayList<Item>  {
        futureRequest = RequestFuture.newFuture()
        val mQueue = CustomVolleyRequestQueue.getInstance(context).requestQueue
        val jsonBody = JSONObject()///cuando se asocia  parametros a las consultas
        println(ApiRest.GET_ITEMS+PAGE.toString())
        val jsonRequest = object : JsonObjectRequest(
            Method.GET,
            ApiRest.GET_ITEMS+PAGE.toString(),
            jsonBody,
            futureRequest,
            futureRequest
        ) {
            override fun getHeaders(): HashMap<String, String> {
                val token = "" ///Aqui se asigna el token utilizado en la sesión get_token()
                val params = HashMap<String, String>()
                 if (token.isNotEmpty()) {
                    params["Authorization"] = "bearer $token"
                }
                params["Content-Type"] = "application/json;charset=UTF-8"
                params["Accept"] = "application/json"
                return params
            }
        }
        jsonRequest.tag = TAG.URL_GET_LIST_ITEMS
        jsonRequest.retryPolicy = DefaultRetryPolicy(
            20000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        mQueue.add(jsonRequest)
        val data = ArrayList<Item>()
        try {
            val response = futureRequest.get(20, TimeUnit.SECONDS)
            Log.d("msg_new_token", response.toString())
            val recsListItems = response.getJSONArray("results")
            if (recsListItems.length() > 0) {
                for (i in 0 until recsListItems.length()) {
                     val rec = recsListItems.getJSONObject(i)

                    val id = rec.getString("id")
                    val name = rec.getString("name")
                    val status = rec.getString("status")
                    val species = rec.getString("species")
                    val type = rec.getString("type")
                    val gender = rec.getString("gender")
                    val image = rec.getString("image")
                    val origin = rec.getJSONObject("origin")
                    val originName = origin.getString("name")
                    val originUrl = origin.getString("url")


                    val location = rec.getJSONObject("location")

                    val locationName = location.getString("name")
                    val locationUrl = location.getString("url")
                    data.add(
                        Item(id, name, status, species, type, gender,image,
                        itemOrigin(originName,originUrl),
                        location(locationName,locationUrl)
                    )
                    )
                }
                return data
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: TimeoutException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            if (e.message == "com.android.volley.AuthFailureError") {
                // Log.d("error", "Autentificación para generar un nuevo token");
                val jsonRequestNewToken = object : JsonObjectRequest(
                    Method.GET,
                    ApiRest.GET_ITEMS+PAGE.toString(),
                    jsonBody,
                    futureRequest,
                    futureRequest
                ) {
                    override fun getHeaders(): HashMap<String, String> {
                        val token = "" ///Aqui se asigna el token utilizado en la sesión get_token()
                        val params = HashMap<String, String>()
                         if (token.isNotEmpty()) {
                        params["Authorization"] = "beare $token"
                        }
                        params["Content-Type"] = "application/json;charset=UTF-8"
                        params["Accept"] = "application/json"
                        return params
                    }
                }
                val responseNewToken = futureRequest.get(20, TimeUnit.SECONDS)
                val recsListItemsAfterNewToken = responseNewToken.getJSONArray("results")

                mQueue.add(jsonRequestNewToken)

             if (recsListItemsAfterNewToken.length() > 0) {
                    for (i in 0 until recsListItemsAfterNewToken.length()) {
                        val rec = recsListItemsAfterNewToken.getJSONObject(i)
                        val id = rec.getString("id")
                        val name = rec.getString("name")
                        val status = rec.getString("status")
                        val species = rec.getString("species")
                        val type = rec.getString("type")
                        val gender = rec.getString("gender")
                        val image = rec.getString("image")


                        val origin = rec.getJSONObject("origin")
                        val originName = origin.getString("gender")
                        val originUrl = origin.getString("gender")


                        val location = rec.getJSONObject("location")
                        val locationName = location.getString("name")
                        val locationUrl = location.getString("url")
                        data.add(
                            Item(id, name, status, species, type, gender,image,
                                itemOrigin(originName,originUrl),
                                location(locationName,locationUrl)
                            )
                        )
                    }
                    return data
                }
            }
        } catch (ee: RuntimeException) {
            ee.printStackTrace()
        }
         return data
    }
}