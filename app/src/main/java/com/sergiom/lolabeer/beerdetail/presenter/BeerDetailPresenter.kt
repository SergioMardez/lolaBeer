package com.sergiom.lolabeer.beerdetail.presenter

import android.os.Bundle
import com.sergiom.lolabeer.app.LolaBeerApp
import com.sergiom.lolabeer.beers.model.Beer
import com.sergiom.lolabeer.beers.model.Labels

class BeerDetailPresenter {
    lateinit var beerId: String
    lateinit var beerImage: String
    lateinit var detailBeerName: String
    lateinit var beerDescription: String
    lateinit var style: String
    var adapterPos = 0
    private var myFavourite = false

    fun getBundleData(bundle: Bundle) {
        beerId = bundle.get("beerId").toString()
        beerImage = bundle.get("beerImage").toString()
        detailBeerName = bundle.get("beerName").toString()
        beerDescription = bundle.get("beerDescription").toString()
        adapterPos = bundle.getInt("adapterPos")
        style = bundle.get("style").toString()
        checkFavourite()
    }

    private fun checkFavourite() {
        val favBeers = LolaBeerApp.instance.getFavourites()
        for (beer in favBeers) {
            if (beer.idBeer == beerId) {
                myFavourite = true
                break
            }
        }
    }

    fun getTheBeer(): Beer {
        val beer = Beer()
        beer.idBeer = beerId
        beer.description = beerDescription
        val labels = Labels()
        labels.large = beerImage
        beer.labels = labels
        beer.beerName = detailBeerName
        return beer
    }

    fun isFavourite(): Boolean {
        return myFavourite
    }
}