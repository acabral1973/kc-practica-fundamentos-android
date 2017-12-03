package es.smartech.foodr.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import es.smartech.foodr.R
import es.smartech.foodr.models.Dish

class OrderRecyclerViewAdapter(val order: List<Dish>?) : RecyclerView.Adapter<OrderRecyclerViewAdapter.DishViewHolder>() {

    var onClickListener : View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.cell_dishes, parent, false)
        view.setOnClickListener(onClickListener)
        return DishViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (order != null) {
            return order.size
        } else {
            return (0)
        }
    }

    override fun onBindViewHolder(holder: DishViewHolder?, position: Int) {

        if (order != null) {
            val dish = order[position]
            val dishName = dish.name
            val dishCategory = dish.category.toString()
            val dishImage = dish.image
            val dishPrice = dish.price

            holder?.bindTable(dishImage, dishName, dishCategory, dishPrice)
        }
    }

    inner class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dishImage = itemView.findViewById<ImageView>(R.id.dish_image)
        val dishName = itemView.findViewById<TextView>(R.id.dish_name)
        val dishCategory = itemView.findViewById<TextView>(R.id.dish_category)
        val dishPrice = itemView.findViewById<TextView>(R.id.dish_price)

        fun bindTable(image: Int, name: String, category: String, price: Float) {
            dishImage.setImageResource(image)
            dishName.text = name
            dishCategory.text = category
            dishPrice.text = "${price.toString()} €"
        }
    }
}