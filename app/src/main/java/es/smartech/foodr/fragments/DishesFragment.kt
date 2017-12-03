package es.smartech.foodr.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import es.smartech.foodr.models.Dish
import es.smartech.foodr.models.Restaurant

class DishesFragment : Fragment() {
    companion object {
        val REQUEST_ADD_DISH = 1
        val ARG_RESTAURANT = "ARG_RESTAURANT"
        val ARG_MODE = "ARG_MODE"

        fun newInstance(restaurant: Restaurant?, addMode: Boolean): DishesFragment {
            val fragment = DishesFragment()
            val arguments = Bundle()

            arguments.putSerializable(ARG_RESTAURANT, restaurant)
            arguments.putBoolean(ARG_MODE, addMode)
            fragment.arguments = arguments

            return fragment
        }
    }

    lateinit var restaurant : Restaurant
    lateinit var recyclerViewDishes : RecyclerView
    lateinit var fragmentView : View
    var onFragmentAddDishListener : OnFragmentAddDishListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (inflater != null) {
            fragmentView = inflater.inflate(R.layout.fragment_dishes, container, false)
            recyclerViewDishes = fragmentView.findViewById(R.id.recycler_view_dishes)
            restaurant = arguments.getSerializable(ARG_RESTAURANT) as Restaurant
            val addMode = arguments.getBoolean(ARG_MODE)

            // dishs almacena la lista de platos que descagamos de intrnet y que mostrará el RecyclrView
            val dishes = restaurant.menu

            // construyo el Adapter para el RecyclerViw
            val adapter = DishesRecyclerViewAdapter(dishes)
            adapter.onClickListener = View.OnClickListener { view ->
                val position = recyclerViewDishes.getChildAdapterPosition(view)
                val dishToShow = dishes[position]
                startActivityForResult(DishDetailActivity.intent(activity, dishToShow, addMode), REQUEST_ADD_DISH)
            }
            // Asigno LayoutManager, ItemAnimator y Adapter para el RecyclerView
            recyclerViewDishes.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewDishes.itemAnimator = DefaultItemAnimator()
            recyclerViewDishes.adapter = adapter

        }
        return fragmentView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ADD_DISH) {
            if (resultCode == Activity.RESULT_OK) {
                val dish = data?.getSerializableExtra(DishDetailActivity.EXTRA_DISH) as Dish
                onFragmentAddDishListener?.dishIsAdded(dish)
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
        if (context is OnFragmentAddDishListener) {
            onFragmentAddDishListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        onFragmentAddDishListener = null
    }

    interface OnFragmentAddDishListener{
        fun dishIsAdded(dish: Dish)
    }
}
