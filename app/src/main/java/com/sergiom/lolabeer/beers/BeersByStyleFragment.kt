package com.sergiom.lolabeer.beers

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sergiom.lolabeer.R
import com.sergiom.lolabeer.api.ApiConstants
import com.sergiom.lolabeer.api.BrewerydbService
import com.sergiom.lolabeer.app.LolaBeerApp
import com.sergiom.lolabeer.beerdetail.BeerDetailFragment
import com.sergiom.lolabeer.interfaces.ItemSelectedListener
import com.sergiom.lolabeer.beers.adapter.BeersByStyleRecyclerViewAdapter
import com.sergiom.lolabeer.beers.model.Beer
import com.sergiom.lolabeer.beers.model.BeerListResponse
import com.sergiom.lolabeer.beers.presenter.BeersByStyleInterface
import com.sergiom.lolabeer.beers.presenter.BeersByStylePresenter
import com.sergiom.lolabeer.beerstyle.BeerStyleFragment
import kotlinx.android.synthetic.main.fragment_beers_by_style.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception

/**
 * Fragment with the beers by style list view.
 */
class BeersByStyleFragment : Fragment(), ItemSelectedListener, BeersByStyleInterface {

    private val beersByStylePresenter = BeersByStylePresenter(this)
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterBeersByStyle: BeersByStyleRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = this.arguments
        beersByStylePresenter.getBundleDate(bundle!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_beers_by_style, container, false)

        //Override BackButton
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                goBackToPreviousFragment()
                return@OnKeyListener true
            }
            false
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (beersByStylePresenter.getPositionToReload() != 0 && beersByStylePresenter.getBeersSelected().isNotEmpty()) {
            setRecyclerView(beersByStylePresenter.getBeersSelected(), beersByStylePresenter.getFavBeers())
        } else {
            beersByStylePresenter.callToApi(0)
        }
    }

    override fun setRecyclerView(beers: ArrayList<Beer>, favBeers: ArrayList<Beer>) {
        layoutManager = GridLayoutManager(this.context,3)
        beers_by_style_recycler_view.layoutManager = layoutManager

        adapterBeersByStyle = if (beersByStylePresenter.getPositionToReload() != 0 && beers.isNotEmpty()) {
            BeersByStyleRecyclerViewAdapter(beers, favBeers, this)
        } else {
            BeersByStyleRecyclerViewAdapter(beers, favBeers, this)
        }

        beers_by_style_recycler_view.adapter = adapterBeersByStyle
    }

    override fun updateRecycler(beers: ArrayList<Beer>) {
        adapterBeersByStyle?.addRows(beers)
    }

    override fun onItemSelected(bundle: Bundle) {
        LolaBeerApp.instance.storeAllStyleBeers(adapterBeersByStyle!!.getAllBeersByStyle())
        LolaBeerApp.instance.storePage(beersByStylePresenter.getCurrentPage())
        val beersDetailFragment = BeerDetailFragment()
        bundle.putString("style", beersByStylePresenter.getCurrentStyle())
        beersDetailFragment.arguments =  bundle

        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.content_main, beersDetailFragment)?.addToBackStack(null)?.commit()
    }

    private fun goBackToPreviousFragment() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        val styleFragment = BeerStyleFragment()

        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.content_main, styleFragment)?.commit()
    }
}