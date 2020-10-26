package com.sergiom.lolabeer.api

import com.sergiom.lolabeer.beers.model.BeerListResponse
import com.sergiom.lolabeer.beerstyle.model.BeerStyleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BrewerydbService {
        @GET("categories")
        fun getBeerStyles(@Query("key") key: String) : Call<BeerStyleResponse>

        @GET("beers")
        fun getBeers(@Query("key") key: String) : Call<BeerListResponse>

        @GET("beers")
        fun getBeersNextPage(@Query("p") pageNum: String, @Query("key") key: String) : Call<BeerListResponse>
}