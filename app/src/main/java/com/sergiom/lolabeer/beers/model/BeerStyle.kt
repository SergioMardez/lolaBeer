package com.sergiom.lolabeer.beers.model

import com.google.gson.annotations.SerializedName

class BeerStyle {
    @SerializedName("category")
    var category: BeerCategoryInStyle? = null
    @SerializedName("name")
    var categoryName: String? = null
    @SerializedName("description")
    var description: String? = null
}