package com.sergiom.lolabeer.favourites

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sergiom.lolabeer.MainActivity
import com.sergiom.lolabeer.R
import com.sergiom.lolabeer.app.LolaBeerApp
import com.sergiom.lolabeer.beers.adapter.BeersByStyleRecyclerViewAdapter
import com.sergiom.lolabeer.beers.model.Beer
import com.sergiom.lolabeer.beerstyle.BeerStyleFragment
import com.sergiom.lolabeer.interfaces.ItemSelectedListener
import kotlinx.android.synthetic.main.fragment_beers_by_style.*

/**
 * Fragment that gets the favourite beers and show them using the beers by style recyclerView.
 */
class FavouritesFragment: Fragment(), ItemSelectedListener {

    private lateinit var favList: ArrayList<Beer>
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterFavs: RecyclerView.Adapter<BeersByStyleRecyclerViewAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favList = LolaBeerApp.instance.getFavourites()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_beers_by_style, container, false)

        (activity as MainActivity).supportActionBar?.title = "Favourites"
        //Add the arrow on the left side of the name
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(true)

        //Override BackButton
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                goBackToStyleFragment()
                return@OnKeyListener true
            }
            false
        })
        return view
    }

    private fun goBackToStyleFragment() {
        val beerStyleFragment = BeerStyleFragment()

        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.content_main, beerStyleFragment)?.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        layoutManager = GridLayoutManager(this.context,3)
        beers_by_style_recycler_view.layoutManager = layoutManager

        adapterFavs = BeersByStyleRecyclerViewAdapter(favList, arrayListOf(), this)

        beers_by_style_recycler_view.adapter = adapterFavs
    }

    override fun onItemSelected(bundle: Bundle) {
        //If you want to show detail in the favourite view
    }
}