package com.sergiom.lolabeer.beerstyle.presenter

import com.sergiom.lolabeer.beerstyle.model.Style

interface StyleInterface {
    fun setRecyclerView(styleList: ArrayList<Style>)
}