package com.moucan.business.busy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moucan.business.R

/**
 * @Classname: FragmentViewPager
 * @Description:
 * @Since: 4/29/21 3:56 PM
 * @Author: moucan
 */
class ViewPagerAdapter(var mData: List<String>) : RecyclerView.Adapter<ViewPagerAdapter.CardViewHolder>() {

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.tv_title)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.textView.text = mData[position]
    }

    override fun getItemCount() = mData.size




}