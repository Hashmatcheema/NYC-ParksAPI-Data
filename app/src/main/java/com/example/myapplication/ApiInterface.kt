package com.example.myapplication

import MyData
import MyDataItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET(value ="resource/enfh-gkve.json")
    fun getData():Call<List<MyDataItem>>
}