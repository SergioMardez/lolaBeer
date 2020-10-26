package com.sergiom.lolabeer.beers.presenter

import android.os.Bundle
import com.sergiom.lolabeer.api.ApiConstants
import com.sergiom.lolabeer.api.BrewerydbService
import com.sergiom.lolabeer.app.LolaBeerApp
import com.sergiom.lolabeer.beers.model.Beer
import com.sergiom.lolabeer.beers.model.BeerListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception

class BeersByStylePresenter(beersByStyleInterface: BeersByStyleInterface) {
    private val beersBySInterface = beersByStyleInterface
    private lateinit var retrofit: Retrofit
    private var pageNumCall = 1
    private lateinit var beersByStyles: BeerListResponse
    private var styleOfBeers: String = ""
    private lateinit var favList: ArrayList<Beer>
    private var positionToReload = -1
    private lateinit var beersSelected: ArrayList<Beer>

    fun getBundleDate(bundle: Bundle) {
        styleOfBeers = bundle.getString("style")!!

        try {
            positionToReload = bundle.getInt("adapterPos")
            beersSelected = LolaBeerApp.instance.getAllStyleBeers()
            pageNumCall = LolaBeerApp.instance.getPage()
        } catch(exception: Exception){
        }
        favList = LolaBeerApp.instance.getFavourites()
    }

    fun callToApi(pageNum: Int) {
        retrofit = LolaBeerApp().getRetrofit()

        val service = retrofit.create(BrewerydbService::class.java)
        val call = if (pageNum == 0) {
            service.getBeers(ApiConstants.key)
        } else {
            service.getBeersNextPage("" + ++pageNumCall, ApiConstants.key)
        }

        call.enqueue(object : Callback<BeerListResponse> {
            override fun onResponse(
                call: Call<BeerListResponse>,
                response: Response<BeerListResponse>
            ) {
                if (response.code() == 200) {
                    if (pageNum == 0) {
                        beersByStyles = response.body()!!
                        beersBySInterface.setRecyclerView(checkStyles(beersByStyles.data), favList)
                        callToApi(1)
                    } else if (pageNumCall <= response.body()!!.numberOfPages) {
                        beersByStyles = response.body()!!
                        updateRecycler(checkStyles(beersByStyles.data))
                    }
                }
            }

            override fun onFailure(call: Call<BeerListResponse>, t: Throwable) {

            }
        })
    }

    private fun updateRecycler(beerUpdate: ArrayList<Beer>) {
        if (beerUpdate.isNotEmpty()) {
            beersBySInterface.updateRecycler(beerUpdate)
        }
        callToApi(pageNumCall)
    }

    private fun checkStyles(beerList: ArrayList<Beer>): ArrayList<Beer> {
        val selectedList = arrayListOf<Beer>()

        for (beer in beerList) {
            if (styleOfBeers == beer.beerStyle?.category?.nameCategory) {
                selectedList.add(beer)
            }
        }

        return selectedList
    }

    fun getBeersSelected(): ArrayList<Beer> {
        return this.beersSelected
    }

    fun getFavBeers(): ArrayList<Beer>{
        return this.favList
    }

    fun getPositionToReload(): Int {
        return this.positionToReload
    }

    fun getCurrentPage(): Int {
        return this.beersByStyles.currentPage
    }

    fun getCurrentStyle(): String {
        return styleOfBeers
    }
}