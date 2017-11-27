package es.smartech.foodr.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.smartech.foodr.R
import es.smartech.foodr.activities.TablesActivity
import es.smartech.foodr.adapters.TablesRecyclerViewAdapter
import es.smartech.foodr.models.Restaurant
import kotlinx.android.synthetic.main.activity_tables.*

class TablesFragment : Fragment() {

    companion object {

        val ARG_RESTAURANT = "ARG_RESTAURANT"

        fun newInstance(restaurant: Restaurant?): TablesFragment {
            val fragment = TablesFragment()
            val arguments = Bundle()

            arguments.putSerializable(ARG_RESTAURANT, restaurant)
            fragment.arguments = arguments

            return fragment
        }
    }

    lateinit var restaurant : Restaurant

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater?.inflate(R.layout.fragment_tables, container, false)

        restaurant = arguments.getSerializable(ARG_RESTAURANT) as Restaurant
        val numberOfTables = restaurant.numberOfTables

        // Configuro LayoutManager, ItemAnimator y Adapter para el RecyclerView
        recyclerViewTables.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerViewTables.itemAnimator = DefaultItemAnimator()
        recyclerViewTables.adapter = TablesRecyclerViewAdapter(numberOfTables)
        return fragmentView
    }
}
