package com.sergiom.lolabeer.beerdetail

import android.R.attr
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sergiom.lolabeer.R
import com.sergiom.lolabeer.app.LolaBeerApp
import com.sergiom.lolabeer.beerdetail.presenter.BeerDetailPresenter
import com.sergiom.lolabeer.beers.BeersByStyleFragment
import com.sergiom.lolabeer.beers.model.Beer
import com.sergiom.lolabeer.beers.model.Labels
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_beer_detail.*
import uk.co.senab.photoview.PhotoViewAttacher


class BeerDetailFragment: Fragment() {
    private val detailPresenter = BeerDetailPresenter()
    private var myFavourite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        val bundle = this.arguments
        detailPresenter.getBundleData(bundle!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_beer_detail, container, false)

        myFavourite = detailPresenter.isFavourite()

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

    private fun goBackToPreviousFragment() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        val bundle = Bundle()
        bundle.putInt("adapterPos", detailPresenter.adapterPos)
        bundle.putString("style", detailPresenter.style)
        val beerByStyleFragment = BeersByStyleFragment()
        beerByStyleFragment.arguments =  bundle

        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.content_main, beerByStyleFragment)?.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (detailPresenter.isFavourite()) {
            favourite.background = ContextCompat.getDrawable(
                favourite.context,
                R.drawable.ic_my_favorite_24
            )
        }

        val size = 900
        Picasso.get().load(detailPresenter.beerImage).resize(size, size).into(beer_detail_image)

        beer_detail_name.text = detailPresenter.detailBeerName
        beer_description.text = detailPresenter.beerDescription

        detail_close_button.setOnClickListener{
            goBackToPreviousFragment()
        }

        beer_detail_image.setOnClickListener {
            val pictureDialog = PictureDialogFragment()
            val bundle = Bundle()
            bundle.putString("picture", detailPresenter.beerImage)
            pictureDialog.arguments = bundle
            pictureDialog.show(fragmentManager!!, null)
        }

        favourite.setOnClickListener {
            if(!myFavourite) {
                it.background = ContextCompat.getDrawable(it.context, R.drawable.ic_my_favorite_24)
                it.tag = "favourite"
                myFavourite = true
                detailPresenter.storeToShared()
            } else {
                it.background = ContextCompat.getDrawable(
                    it.context,
                    R.drawable.ic_not_favorite_border_24
                )
                it.tag = "notFavourite"
                myFavourite = false
                detailPresenter.deleteFromShared()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}