package com.wlkmultimedia.ui.main.adapters.homeadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wlkmultimedia.R
import com.wlkmultimedia.model.HomeSubModel
import kotlinx.android.synthetic.main.home_sub_recycler_item.view.*

class HomeSubAdapter(val list:ArrayList<HomeSubModel>,val click:(vh:ViewHolder,pos:Int)->Unit):RecyclerView.Adapter<HomeSubAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        return  ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_sub_recycler_item,parent,false))
    }


    override fun getItemCount()=list.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv.text = list[position].tv
        //Glide.with(context).load(list[position].iv).into(holder.iv)
        holder.iv.setImageResource(list[position].iv)
        holder.itemView.setOnClickListener{click(holder,position)}
    }
    class ViewHolder(v:View):RecyclerView.ViewHolder(v) {
        val tv = v.tvHomeSubItem
        val iv = v.ivHomeSubItem
    }

}