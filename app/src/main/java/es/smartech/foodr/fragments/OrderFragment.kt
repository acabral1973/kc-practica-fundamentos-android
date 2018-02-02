package es.smartech.foodr.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import es.smartech.foodr.models.Restaurant
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ViewSwitcher
import es.smartech.foodr.R
import es.smartech.foodr.adapters.OrderRecyclerViewAdapter
import es.smartech.foodr.models.Allergen
import es.smartech.foodr.models.Category
import es.smartech.foodr.models.Dish
import kotlinx.android.synthetic.main.fragment_order.*

class OrderFragment : Fragment() {
    companion object {

        enum class VIEW_INDEX(val index: Int) {
            NO_ORDER(0),
            ORDER(1)
        }

        private val ARG_RESTAURANT = "ARG_RESTAURANT"
        private val ARG_TABLE_NUMBER = "ARG_TABLE_NUMBER"

        fun newInstance(restaurant: Restaurant, tableNumber: Int): OrderFragment {
            val fragment = OrderFragment()
            val arguments = Bundle()

            arguments.putSerializable(ARG_RESTAURANT, restaurant)
            arguments.putInt(ARG_TABLE_NUMBER, tableNumber)
            fragment.arguments = arguments

            return fragment
        }
    }

    lateinit var restaurant : Restaurant
    var tableNumber : Int = 0
    lateinit var recyclerViewOrder : RecyclerView
    lateinit var fragmentView : View
    lateinit var viewSwitch : ViewSwitcher

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (inflater != null) {
            fragmentView = inflater?.inflate(R.layout.fragment_order, container, false)
            recyclerViewOrder = fragmentView.findViewById(R.id.recycler_view_order)
            viewSwitch = fragmentView.findViewById(R.id.view_switcher)

            restaurant = arguments.getSerializable(ARG_RESTAURANT) as Restaurant
            tableNumber = arguments.getInt(ARG_TABLE_NUMBER)
            val restaurantName = restaurant.name
            var order = restaurant.tables[tableNumber].order

            if (order.isEmpty()){
                viewSwitch.displayedChild = VIEW_INDEX.NO_ORDER.index
            } else {
                viewSwitch.displayedChild = VIEW_INDEX.ORDER.index
            }

            // construyo el Adapter para el RecyclerViw
            val adapter = OrderRecyclerViewAdapter(order)
            adapter.onClickListener = View.OnClickListener { view ->
                val orderPosition = recyclerViewOrder.getChildAdapterPosition(view)
                Log.d("OrderFragment", "Configurando el listener")
                // esto es lo que haré cuando pulsan sobre un plato de la orden
                AlertDialog.Builder(activity)
                        .setTitle("Notas del cliente")
                        .setMessage(order[orderPosition].customerNotes)
                        .setPositiveButton("Aceptar", {dialog, _ ->
                            dialog.dismiss()
                        }).show()
            }

            // Configuro LayoutManager, ItemAnimator y Adapter para el RecyclerView
            recyclerViewOrder.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewOrder.itemAnimator = DefaultItemAnimator()
            recyclerViewOrder.adapter = adapter

        }
        return fragmentView
    }


    fun updateOrder (updatedRestaurant: Restaurant) {
        restaurant = updatedRestaurant
        val adapter = OrderRecyclerViewAdapter(restaurant.tables[tableNumber].order)
        recyclerViewOrder.adapter = adapter
        viewSwitch.displayedChild = VIEW_INDEX.ORDER.index
    }
}
