package com.moucan.pagingsimple.nested

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moucan.pagingsimple.R

class NormalItemAdapter constructor(val string: ArrayList<String>): RecyclerView.Adapter<NormalItemAdapter.ViewHolder>() {


    class ViewHolder constructor(private val itemView: View): RecyclerView.ViewHolder(itemView){
        val tvContent: TextView = itemView.findViewById(R.id.tv_content)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_normal, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvContent.text = string[position]
    }

    override fun getItemCount() = string.size

    fun addAll(str: ArrayList<String>){
        string.addAll(str)
        notifyDataSetChanged()
    }

}