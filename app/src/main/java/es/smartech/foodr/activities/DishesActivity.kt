package es.smartech.foodr.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import es.smartech.foodr.R
import es.smartech.foodr.adapters.DishesRecyclerViewAdapter
import es.smartech.foodr.models.Restaurant
import kotlinx.android.synthetic.main.activity_dishes.*


class DishesActivity : AppCompatActivity() {

    companion object {

        val EXTRA_RESTAURANT = "EXTRA_RESTAURANT"

        fun intent(context: Context, restaurant: Restaurant) : Intent {
            val intent = Intent(context, DishesActivity::class.java)
            intent.putExtra(EXTRA_RESTAURANT, restaurant)
            return intent
        }
    }

    lateinit var restaurant : Restaurant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dishes)

        restaurant = intent.getSerializableExtra(EXTRA_RESTAURANT) as Restaurant
        val nameOfRestaurant = restaurant.name
        val dishes = restaurant.menu

        supportActionBar?.title = "${nameOfRestaurant} (Carta)"

        // Configuro LayoutManager, ItemAnimator y Adapter para el RecyclerView
        recyclerViewDishes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewDishes.itemAnimator = DefaultItemAnimator()
        recyclerViewDishes.adapter = DishesRecyclerViewAdapter(dishes)

    }
}




