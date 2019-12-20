package com.wlkmultimedia.ui.main.adapters.liveadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wlkmultimedia.R
import com.wlkmultimedia.ui.main.ext.load
import com.wlkmultimedia.ui.main.model.AllLiveObject
import kotlinx.android.synthetic.main.all_live_item.view.*

class EntertainmentAdapter(val l:ArrayList<AllLiveObject>) : RecyclerView.Adapter<EntertainmentAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.all_live_item,parent,false))
    }

    override fun getItemCount() = l.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        l[position].imgLink?.let {
            holder.iv.load(it)
        }
        holder.tvTitle.text = l[position].title
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val tvTitle = v.tvTitle
        val iv = v.ivPhoto
    }

}