package es.smartech.foodr.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.smartech.foodr.R
import es.smartech.foodr.activities.DishDetailActivity
import es.smartech.foodr.activities.OrderManagerActivity
import es.smartech.foodr.adapters.DishesRecyclerViewAdapter
import es.smartech.foodr.adapters.OrderRecyclerViewAdapter

import es.smartech.foodr.adapters.TablesRecyclerViewAdapter
import es.smartech.foodr.models.Restaurant
import es.smartech.foodr.models.Table

import kotlinx.android.synthetic.main.fragment_tables.*

class TablesFragment : Fragment() {

    companion object {

        val REQUEST_ORDER = 1
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
    lateinit var recyclerViewTables : RecyclerView
    lateinit var fragmentView : View
    var onFragmentTableIsUpdatdListener : OnFragmentTableIsUpdatdListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (inflater != null) {
            fragmentView = inflater?.inflate(R.layout.fragment_tables, container, false)
            recyclerViewTables = fragmentView.findViewById<RecyclerView>(R.id.recycler_view_tables)
            restaurant = arguments.getSerializable(ARG_RESTAURANT) as Restaurant
            val restaurantName = restaurant.name

            // construyo el Adapter para el RecyclerViw
            val adapter = TablesRecyclerViewAdapter(restaurant)
            adapter.onClickListener = View.OnClickListener { view ->
                val tableNumber = recyclerViewTables.getChildAdapterPosition(view)

                startActivityForResult(OrderManagerActivity.intent(activity, restaurant, tableNumber), REQUEST_ORDER)
            }

            // Configuro LayoutManager, ItemAnimator y Adapter para el RecyclerView
            recyclerViewTables.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewTables.itemAnimator = DefaultItemAnimator()
            recyclerViewTables.adapter = adapter

        }
        return fragmentView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ORDER) {
            if (resultCode == Activity.RESULT_OK) {
                restaurant = data?.getSerializableExtra(OrderManagerActivity.EXTRA_RESTAURANT) as Restaurant
                val adapter = TablesRecyclerViewAdapter(restaurant)
                adapter.onClickListener = View.OnClickListener { view ->
                    val tableNumber = recyclerViewTables.getChildAdapterPosition(view)

                    startActivityForResult(OrderManagerActivity.intent(activity, restaurant, tableNumber), REQUEST_ORDER)
                }
                recyclerViewTables.adapter = adapter
                onFragmentTableIsUpdatdListener?.tableIsUpdated(restaurant)
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        commonOnattach(context)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        commonOnattach(activity)
    }

    fun commonOnattach(context: Context?) {
        // Me quedo con la referencia de la actividad a la que me atacheo para poder avisarle "cosas"
        if (context is OnFragmentTableIsUpdatdListener) {
            onFragmentTableIsUpdatdListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        onFragmentTableIsUpdatdListener = null
    }

    interface OnFragmentTableIsUpdatdListener{
        fun tableIsUpdated(restaurant: Restaurant)
    }
}
