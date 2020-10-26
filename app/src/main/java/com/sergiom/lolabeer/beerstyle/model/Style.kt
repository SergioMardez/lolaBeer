package com.sergiom.lolabeer.beerstyle.model

import com.google.gson.annotations.SerializedName

class Style {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("createDate")
    var createDate: String? = null
}