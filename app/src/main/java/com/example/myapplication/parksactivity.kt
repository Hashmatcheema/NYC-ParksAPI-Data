package com.example.myapplication

import MyDataItem
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class parksactivity : AppCompatActivity() {
    lateinit var myAdapter: parksadapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recyclerViewParks: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parksactivity)
        recyclerViewParks = findViewById(R.id.recyclerview_parks)
        recyclerViewParks.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerViewParks.layoutManager = linearLayoutManager

        // Retrieve the borough passed from MainActivity
        val borough = intent.getStringExtra("borough") ?: return

        getMyData(borough)  // Fetch parks based on the borough
    }

    private fun getMyData(borough: String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val call = retrofit.getData()  // Ensure this method is supposed to fetch relevant data based on the borough if necessary

        call.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(call: Call<List<MyDataItem>?>, response: Response<List<MyDataItem>?>) {
                val responseBody = response.body()

                if (responseBody != null && responseBody.isNotEmpty()) {
                    val filteredItems = responseBody.filter {
                        it.borough == borough && !it.eapply.isNullOrBlank()
                    }
                    myAdapter = parksadapter(baseContext, filteredItems)
                    recyclerViewParks.adapter = myAdapter
                } else {
                    Log.e("ParksActivity", "No parks found for borough: $borough")
                    // Optionally, display a message or an empty view here
                }
            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                Log.e("ParksActivity", "onFailure: " + t.message)
            }
        })
    }
}
