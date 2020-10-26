package com.sergiom.lolabeer.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sergiom.lolabeer.api.ApiConstants
import com.sergiom.lolabeer.beers.model.Beer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


class LolaBeerApp: Application() {

    private var retrofit: Retrofit? = null
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var activity: Activity

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    fun getShared(activity: Activity) {
        instance.activity = activity
        sharedPreferences = activity.getSharedPreferences(
            "lolaPref",
            Context.MODE_PRIVATE
        )
    }

    fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit!!
    }

    fun storePage(numPage: Int) {
        checkShared()
        val editor = sharedPreferences?.edit()
        editor?.putInt("page", numPage)
        editor?.apply()
    }

    fun getPage(): Int {
        checkShared()
        return sharedPreferences?.getInt("page", 0)!!
    }

    fun storeFavourite(beer: Beer) {
        checkShared()
        val storedFavourites = getFavourites()
        storedFavourites.add(beer)

        storeList(storedFavourites, "beers")
    }

    fun getFavourites(): ArrayList<Beer> {
        checkShared()
        return getList("beers")
    }

    fun storeAllStyleBeers(list: ArrayList<Beer>) {
        checkShared()
        val editor = sharedPreferences?.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor?.putString("allBeers", json)
        editor?.apply()
    }

    fun getAllStyleBeers(): ArrayList<Beer> {
        checkShared()
        return getList("allBeers")
    }

    private fun storeList(list: ArrayList<Beer>, key: String) {
        val editor = sharedPreferences?.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor?.putString(key, json)
        editor?.apply()
    }

    private fun getList(key: String): ArrayList<Beer> {
        var arrayItems = arrayListOf<Beer>()
        val serializedObject = sharedPreferences?.getString(key, null)
        if (serializedObject != null) {
            val gson = Gson()
            val type: Type = object : TypeToken<ArrayList<Beer?>?>() {}.type
            arrayItems = gson.fromJson(serializedObject, type)
        }
        return arrayItems
    }

    fun deleteFromFavourites(beer: Beer) {
        val storedFavourites = getFavourites()

        for (beerToDelete in storedFavourites) {
            if (beer.idBeer == beerToDelete.idBeer) {
                storedFavourites.remove(beerToDelete)
                break
            }
        }

        val editor = sharedPreferences?.edit()
        val gson = Gson()
        val json = gson.toJson(storedFavourites)
        editor?.putString("beers", json)
        editor?.apply()
    }

    private fun checkShared() {
        if (sharedPreferences == null) {
            getShared(instance.activity)
        }
    }

    companion object {
        lateinit var instance: LolaBeerApp

        fun getAppContext(): Context = instance.applicationContext
    }
}