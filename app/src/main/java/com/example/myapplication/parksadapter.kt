package com.example.myapplication

import MyDataItem
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class parksadapter (val context: Context, val userlist: List<MyDataItem> ): RecyclerView.Adapter<parksadapter.ViewHolder>() {
    lateinit var parktitle: TextView

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
      //  var userId: TextView
        var title: TextView
        var userId: TextView = itemView.findViewById(R.id.parkname)

        init {
            //userId=itemView.findViewById(R.id.userId)
            title   =itemView.findViewById(R.id.parkname)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView= LayoutInflater.from(context). inflate(R.layout.parkrow,parent,false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text=userlist[position].eapply

    }


    override fun getItemCount(): Int {
        return userlist.size

    }


}
