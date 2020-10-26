package com.sergiom.lolabeer.beers.presenter

import com.sergiom.lolabeer.beers.model.Beer

interface BeersByStyleInterface {
    fun setRecyclerView(beers: ArrayList<Beer>, favBeers: ArrayList<Beer>)
    fun updateRecycler(beers: ArrayList<Beer>)
}