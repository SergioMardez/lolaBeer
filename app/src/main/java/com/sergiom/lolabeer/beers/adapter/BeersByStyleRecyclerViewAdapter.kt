package com.sergiom.lolabeer.beers.adapter

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sergiom.lolabeer.R
import com.sergiom.lolabeer.beers.model.Beer
import com.sergiom.lolabeer.interfaces.ItemSelectedListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.beers_by_style_card_view.view.*

class BeersByStyleRecyclerViewAdapter(data: ArrayList<Beer>, fav: ArrayList<Beer>, listener: ItemSelectedListener) : RecyclerView.Adapter<BeersByStyleRecyclerViewAdapter.ViewHolder>() {

    private var beersByStyle = data
    private var beerFavs = fav
    private val beerListener = listener
    private lateinit var v: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        v = LayoutInflater.from(parent.context).inflate(R.layout.beers_by_style_card_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: BeersByStyleRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = beersByStyle[position].beerName

        for (beer in beerFavs) {
            if (beer.idBeer == beersByStyle[position].idBeer!!) {
                holder.itemCardView.setCardBackgroundColor(Color.parseColor("#a3e7ad"))
            }
        }

        if (beersByStyle[position].labels != null) {
            Picasso.get().load(beersByStyle[position].labels!!.large).into(holder.itemImage)
        } else {
            Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(holder.itemImage)
        }
    }

    override fun getItemCount(): Int {
        return beersByStyle.size
    }

    fun addRows(rows: ArrayList<Beer>) {
        val totalBeersBefore = this.beersByStyle.size
        this.beersByStyle.addAll(rows)
        notifyItemRangeInserted(totalBeersBefore, this.beersByStyle.size - 1)
    }

    fun goToPosition(position: Int) {
        notifyItemInserted(position)
    }

    fun getAllBeersByStyle(): ArrayList<Beer> {
        return beersByStyle
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView = itemView.findViewById(R.id.beer_name)
        var itemImage: ImageView = itemView.findViewById(R.id.beer_list_image)
        var itemCardView: CardView = itemView.findViewById(R.id.card_view)

        init {
            itemView.setOnClickListener { v: View ->
                val args = Bundle()
                args.putString("beerId", beersByStyle[adapterPosition].idBeer)
                args.putString("beerName", beersByStyle[adapterPosition].beerName)

                if (beersByStyle[adapterPosition].description.isNullOrEmpty()) {
                    args.putString("beerDescription", beersByStyle[adapterPosition].beerStyle?.description)
                } else {
                    args.putString("beerDescription", beersByStyle[adapterPosition].description)
                }

                if (beersByStyle[adapterPosition].labels == null) {
                    args.putString("beerImage", "http://i.imgur.com/DvpvklR.png")
                } else {
                    args.putString("beerImage", beersByStyle[adapterPosition].labels?.large)
                }
                args.putInt("adapterPos", adapterPosition)

                beerListener.onItemSelected(args)
            }
        }
    }
}