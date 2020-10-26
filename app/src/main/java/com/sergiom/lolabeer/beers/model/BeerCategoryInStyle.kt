package com.sergiom.lolabeer.beers.model

import com.google.gson.annotations.SerializedName

class BeerCategoryInStyle {
    @SerializedName("id")
    var idCategory: Int? = null
    @SerializedName("name")
    var nameCategory: String? = null
    @SerializedName("createDate")
    var createDate: String? = null
}