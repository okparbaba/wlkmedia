package com.wlkmultimedia.ui.main.kidadapter
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wlkmultimedia.R
import com.wlkmultimedia.model.HomeModel
import com.wlkmultimedia.model.HomeSubModel
import kotlinx.android.synthetic.main.kids_recycler_item.view.*
import org.jetbrains.anko.textColor

class KidsRecyclerAdpter(
    val list: ArrayList<HomeModel>, val onClickListener: (vh: ViewHolder, pos: Int) -> Unit
) : RecyclerView.Adapter<KidsRecyclerAdpter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.kids_recycler_item,parent,false))
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position==0){
            holder.title.textColor = Color.WHITE
        }
        holder.title.text = list[position].title
        holder.childRv.apply {
            val lm = LinearLayoutManager(holder.childRv.context)
            lm.orientation = RecyclerView.HORIZONTAL
            layoutManager = lm
            adapter = KidsSubAdapter(list[position].childList as ArrayList<HomeSubModel>){
                vh, pos ->
            }
            isNestedScrollingEnabled = false
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val childRv = v.rvChildHomeItem
        val title = v.tvTitle
    }
}