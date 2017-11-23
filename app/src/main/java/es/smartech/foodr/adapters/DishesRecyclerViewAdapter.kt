package es.smartech.foodr.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import es.smartech.foodr.R
import es.smartech.foodr.models.Dish

class DishesRecyclerViewAdapter(val dishes: List<Dish>) : RecyclerView.Adapter<DishesRecyclerViewAdapter.DishViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.cell_dishes, parent, false)
        return DishViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    override fun onBindViewHolder(holder: DishViewHolder?, position: Int) {
        holder?.bindTable(dishes[position].name, dishes[position].category.toString())
    }

    inner class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cellDishesText = itemView.findViewById<TextView>(R.id.cellDishesText)

        fun bindTable(dishName: String, dishCategory: String) {
            cellDishesText.text = "${dishName} (${dishCategory})"
        }
    }
}