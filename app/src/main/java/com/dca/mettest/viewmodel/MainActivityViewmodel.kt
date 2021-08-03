package com.dca.mettest.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.dca.mettest.model.catagorymodel
import com.dca.mettest.model.itemsmodel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class MainActivityViewmodel : ViewModel() {
    var catagorymodel = MutableLiveData<List<catagorymodel>>()
    val LoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    var context :Context?=null;

    fun refresh(context: Context?){
        this.context=context;
        getdatafromapi()

    }

    fun getdatafromapi(){
        loading.value=true
        LoadError.value=false
        GlobalScope.launch {
            val queue = Volley.newRequestQueue(context)
            val url = "https://devfitser.com/PinkDelivery/dev/api/product/get"
            val stringReq: StringRequest =
                object : StringRequest(Method.POST, url,
                    Response.Listener { response ->
                        // response
                        loading.value = false
                        var strResp = response.toString()

                        var jsonObject = JSONObject(strResp);
                        var statusobj = jsonObject.getJSONObject("status")
                        var resultobj = jsonObject.getJSONObject("result")
                        var errocode = statusobj.getString("error_code")
                        if (errocode.equals("0")) {
                            var catagoryarray = resultobj.getJSONArray("data");

                            var catagorydata = ArrayList<catagorymodel>();

                            catagoryarray?.let {
                                for (i in 0 until catagoryarray.length()) {
                                    var singleitem = catagoryarray.getJSONObject(i)
                                    var itemsdata = ArrayList<itemsmodel>();
                                    val itemarray: JSONArray? = singleitem.getJSONArray("items");
                                    itemarray?.let {
                                        for (i in 0 until itemarray.length()) {
                                            var singleitem = itemarray.getJSONObject(i)
                                            itemsdata.add(
                                                itemsmodel(
                                                    singleitem.getString("product_id"),
                                                    singleitem.getString("product_suk_id"),
                                                    singleitem.getString("product_name"),
                                                    singleitem.getString("category_id"),
                                                    singleitem.getString("description"),
                                                    singleitem.getString("product_image"),
                                                    singleitem.getString("price"),
                                                    singleitem.getString("vendor_id"),
                                                    singleitem.getString("status"),
                                                    singleitem.getString("created_at"),
                                                    singleitem.getString("updated_at"),
                                                    singleitem.getString("category_name"),
                                                    singleitem.getString("vendor_name")
                                                )
                                            )
                                        }
                                    }

                                    catagorydata.add(
                                        catagorymodel(
                                            singleitem.getString("cat_id"),
                                            singleitem.getString("cat_name"),
                                            itemsdata
                                        )
                                    )
                                }
                            }

                            catagorymodel.value = catagorydata;

                        }
                        Log.d("API", catagorymodel.toString())
                        Log.d("API", strResp)
                    },
                    Response.ErrorListener { error ->
                        LoadError.value = true
                        Log.d("API", "error => $error")
                    }
                ) {
                    override fun getBody(): ByteArray {
                        val params = HashMap<String, String>()
                        params.put("store_id", "3")
                        params.put("u_id", "")
                        params.put("access_type", "1")
                        params.put("source", "mob")
                        return JSONObject(params as Map<*, *>).toString().toByteArray()
                    }
                }
            queue.add(stringReq)
        }
    }


}