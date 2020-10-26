package com.sergiom.lolabeer

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.sergiom.lolabeer.app.LolaBeerApp
import com.sergiom.lolabeer.beerstyle.BeerStyleFragment
import com.sergiom.lolabeer.favourites.FavouritesFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val lolaBeerApp = LolaBeerApp()
        lolaBeerApp.getShared(this)

        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.content_main, BeerStyleFragment()).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_favourite -> {
                val favFragment = FavouritesFragment()

                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.content_main, favFragment).addToBackStack(null).commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}