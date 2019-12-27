package com.wlkmultimedia.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wlkmultimedia.R
import com.wlkmultimedia.ui.main.ext.load
import com.wlkmultimedia.ui.main.model.PlayerViewRelatedObject
import kotlinx.android.synthetic.main.player_view_related_item.view.*

class PlayerViewRelatedRecyclerAdapter (val list:ArrayList<PlayerViewRelatedObject>):RecyclerView.Adapter<PlayerViewRelatedRecyclerAdapter.MViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        return MViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.player_view_related_item,parent,false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        val pvlo = list[position]
        holder.title.text = pvlo.title
        holder.image.load(pvlo.imageLink.toString())

    }

    class MViewHolder(v:View):RecyclerView.ViewHolder(v) {
        val title =v.tvTitle
        val image = v.ivImage
    }
}