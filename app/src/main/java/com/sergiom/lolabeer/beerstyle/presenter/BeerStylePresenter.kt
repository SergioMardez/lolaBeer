package com.sergiom.lolabeer.beerstyle.presenter

import com.sergiom.lolabeer.api.ApiConstants
import com.sergiom.lolabeer.api.BrewerydbService
import com.sergiom.lolabeer.app.LolaBeerApp
import com.sergiom.lolabeer.beerstyle.model.BeerStyleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BeerStylePresenter(styleView: StyleInterface) {

    private lateinit var beerStyles: BeerStyleResponse
    private val styleView = styleView

    fun callToApi() {
        val retrofit = LolaBeerApp().getRetrofit()

        val service = retrofit.create(BrewerydbService::class.java)
        val call = service.getBeerStyles(ApiConstants.key)

        call.enqueue(object : Callback<BeerStyleResponse> {
            override fun onResponse(call: Call<BeerStyleResponse>, response: Response<BeerStyleResponse>) {
                if (response.code() == 200) {
                    beerStyles = response.body()!!
                    styleView.setRecyclerView(beerStyles.data)
                }
            }
            override fun onFailure(call: Call<BeerStyleResponse>, t: Throwable) {

            }
        })
    }
}