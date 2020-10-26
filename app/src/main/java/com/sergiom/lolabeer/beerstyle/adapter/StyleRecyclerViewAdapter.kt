package com.sergiom.lolabeer.beerstyle.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sergiom.lolabeer.R
import com.sergiom.lolabeer.interfaces.ItemSelectedListener
import com.sergiom.lolabeer.beerstyle.model.Style

class StyleRecyclerViewAdapter(data: ArrayList<Style>, listener: ItemSelectedListener) : RecyclerView.Adapter<StyleRecyclerViewAdapter.ViewHolder>() {

    private var styles = data
    private val listener = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.style_card_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!styles[position].name!!.contains("test")) {
            holder.itemTitle.text = styles[position].name
        }
    }

    override fun getItemCount(): Int {
        return styles.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView = itemView.findViewById(R.id.style_title)

        init {
            itemView.setOnClickListener { v: View  ->
                val args = Bundle()
                args.putString("style", styles[adapterPosition].name) //To know the style selected
                listener.onItemSelected(args)
            }
        }
    }
}