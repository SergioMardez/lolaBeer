package com.sergiom.lolabeer.beers.model

import com.google.gson.annotations.SerializedName

class BeerListResponse {
    @SerializedName("currentPage")
    var currentPage =  0
    @SerializedName("numberOfPages")
    var numberOfPages =  0
    @SerializedName("data")
    var data =  ArrayList<Beer>()
}