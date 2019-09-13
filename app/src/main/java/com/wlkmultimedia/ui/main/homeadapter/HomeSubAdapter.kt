package com.wlkmultimedia.ui.main.homeadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wlkmultimedia.HomeSubModel
import com.wlkmultimedia.R
import kotlinx.android.synthetic.main.home_sub_recycler_item.view.*

class HomeSubAdapter(val list:List<HomeSubModel>,val context:Context,val click:(vh:ViewHolder,pos:Int)->View.OnClickListener):RecyclerView.Adapter<HomeSubAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.id.home_sub_recyler_item,parent,false))

    override fun getItemCount()=list.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv.text = list[position].tv
        Glide.with(context).load(list[position].iv).into(holder.iv)
        holder.itemView.setOnClickListener(click(holder,position))
    }
    class ViewHolder(v:View):RecyclerView.ViewHolder(v) {
        val tv = v.tvHomeSubItem
        val iv = v.ivHomeSubItem
    }
}