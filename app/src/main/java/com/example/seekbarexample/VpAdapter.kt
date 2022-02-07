package com.example.seekbarexample

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_holder.view.*

class VpAdapter : RecyclerView.Adapter<VpHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VpHolder {
        return VpHolder(parent)
    }

    override fun onBindViewHolder(holder: VpHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return 5*1000
    }

    fun setItem() {
        notifyDataSetChanged()
    }
}

class VpHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_holder, parent, false)
) {

    val view = itemView.view
    val txt = itemView.txt
    fun onBind(position: Int) {
        txt.text = position.toString()
        if (position%2 == 0) {
            view.setBackgroundColor(Color.parseColor("#eeeeee"))
        } else {
            view.setBackgroundColor(Color.parseColor("#FF0000"))
        }
    }
}