package com.example.myapplication

import MyAdaptor
import MyDataItem
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL= "https://nycopendata.socrata.com/"
class MainActivity: AppCompatActivity() {

    lateinit var myAdaptor: MyAdaptor
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recyclerview_users:RecyclerView
    lateinit var myf:LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview_users=findViewById(R.id.recyclerview_users)

        recyclerview_users.setHasFixedSize(true)
        linearLayoutManager= LinearLayoutManager(this    )
        recyclerview_users.layoutManager=linearLayoutManager
        getMydata()

    }

    private fun getMydata()
    {
        val retrofitBuilder= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData= retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(
                call: Call<List<MyDataItem>?>,
                response: Response<List<MyDataItem>?>
            ) {
                val responseBody= response.body()!!
                val allItems = responseBody// your method to load all data items
                val uniqueBoroughs = allItems.distinctBy { it.borough }

                myAdaptor = MyAdaptor(this@MainActivity, uniqueBoroughs) { selectedItem ->
                    val intent = Intent(this@MainActivity, parksactivity::class.java)
                    intent.putExtra("borough", selectedItem.borough)  // Pass the borough name to the ParksActivity
                    startActivity(intent)
                }
                recyclerview_users.adapter = myAdaptor
                myAdaptor.notifyDataSetChanged()
                recyclerview_users.adapter=myAdaptor

            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                d("MainActivity", "onFailure: "+ t.message)
            }
        })


    }
}