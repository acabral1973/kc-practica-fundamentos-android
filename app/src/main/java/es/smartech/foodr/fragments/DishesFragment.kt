package es.smartech.foodr.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import es.smartech.foodr.R
import es.smartech.foodr.activities.DishDetailActivity
import es.smartech.foodr.adapters.DishesRecyclerViewAdapter
import es.smartech.foodr.models.Restaurant

class DishesFragment : Fragment() {
    companion object {

        val ARG_RESTAURANT = "ARG_RESTAURANT"

        fun newInstance(restaurant: Restaurant?): DishesFragment {
            val fragment = DishesFragment()
            val arguments = Bundle()

            arguments.putSerializable(ARG_RESTAURANT, restaurant)
            fragment.arguments = arguments

            return fragment
        }
    }

    lateinit var restaurant : Restaurant
    lateinit var recyclerViewDishes : RecyclerView
    lateinit var fragmentView : View

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (inflater != null) {
            fragmentView = inflater.inflate(R.layout.fragment_dishes, container, false)
            recyclerViewDishes = fragmentView.findViewById(R.id.recycler_view_dishes)
            restaurant = arguments.getSerializable(ARG_RESTAURANT) as Restaurant



            // dishs almacena la lista de platos que descagamos de intrnet y que mostrará el RecyclrView
            val dishes = restaurant.menu

            // construyo el Adapter para el RecyclerViw
            val adapter = DishesRecyclerViewAdapter(dishes)
            adapter.onClickListener = View.OnClickListener { view ->
                val position = recyclerViewDishes.getChildAdapterPosition(view)
                val dishToShow = dishes[position]

                // lo que hago cuando pulsan sobre un plato
                // Toast.makeText(activity, "Han seleccionado ${dish.name}", Toast.LENGTH_LONG).show()
                startActivity(DishDetailActivity.intent(activity, dishToShow))

            }
            // Asigno LayoutManager, ItemAnimator y Adapter para el RecyclerView
            recyclerViewDishes.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewDishes.itemAnimator = DefaultItemAnimator()
            recyclerViewDishes.adapter = adapter

        }
        return fragmentView
    }
}
