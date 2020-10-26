package com.sergiom.lolabeer.beerstyle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sergiom.lolabeer.R
import com.sergiom.lolabeer.api.ApiConstants
import com.sergiom.lolabeer.api.BrewerydbService
import com.sergiom.lolabeer.app.LolaBeerApp
import com.sergiom.lolabeer.beers.BeersByStyleFragment
import com.sergiom.lolabeer.interfaces.ItemSelectedListener
import com.sergiom.lolabeer.beerstyle.adapter.StyleRecyclerViewAdapter
import com.sergiom.lolabeer.beerstyle.model.BeerStyleResponse
import com.sergiom.lolabeer.beerstyle.model.Style
import com.sergiom.lolabeer.beerstyle.presenter.BeerStylePresenter
import com.sergiom.lolabeer.beerstyle.presenter.StyleInterface
import com.sergiom.lolabeer.favourites.FavouritesFragment
import kotlinx.android.synthetic.main.fragment_beer_style.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Fragment with the style list view.
 */
class BeerStyleFragment : Fragment(), ItemSelectedListener,StyleInterface {

    private val stylePresenter = BeerStylePresenter(this)
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterStyle: RecyclerView.Adapter<StyleRecyclerViewAdapter.ViewHolder>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beer_style, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stylePresenter.callToApi()
        fragmentManager?.popBackStack()
    }

    override fun setRecyclerView(styleList: ArrayList<Style>) {
        layoutManager = LinearLayoutManager(this.context)
        style_recycler_view.layoutManager = layoutManager

        adapterStyle = StyleRecyclerViewAdapter(styleList, this)
        style_recycler_view.adapter = adapterStyle
    }

    override fun onItemSelected(bundle: Bundle) {
        val beersByStyleFragment = BeersByStyleFragment()
        beersByStyleFragment.arguments = bundle

        val fragmentManager: FragmentManager = this.fragmentManager!!
        fragmentManager.beginTransaction().replace(
            R.id.content_main,
            beersByStyleFragment
        ).addToBackStack(null).commit()
    }
}