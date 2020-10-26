package com.sergiom.lolabeer.beerstyle.model

import com.google.gson.annotations.SerializedName

class BeerStyleResponse {
    @SerializedName("data")
    var data =  ArrayList<Style>()
}