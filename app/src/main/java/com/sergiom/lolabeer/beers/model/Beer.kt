package com.sergiom.lolabeer.beers.model

import com.google.gson.annotations.SerializedName

class Beer {
    @SerializedName("id")
    var idBeer: String? =  null
    @SerializedName("name")
    var beerName: String? = null
    @SerializedName("description")
    var description: String? =  null
    @SerializedName("labels")
    var labels: Labels? = null
    @SerializedName("style")
    var beerStyle: BeerStyle? = null
}