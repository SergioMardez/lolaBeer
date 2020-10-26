package com.sergiom.lolabeer.beers.model

import com.google.gson.annotations.SerializedName

class Labels {
    @SerializedName("icon")
    var icon: String? = null
    @SerializedName("large")
    var large: String? = null
}