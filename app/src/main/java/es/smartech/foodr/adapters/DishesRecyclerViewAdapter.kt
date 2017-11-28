package es.smartech.foodr.adapters

import android.provider.Settings.Secure.getString
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import es.smartech.foodr.R
import es.smartech.foodr.models.Dish

class DishesRecyclerViewAdapter(val dishes: List<Dish>) : RecyclerView.Adapter<DishesRecyclerViewAdapter.DishViewHolder>() {

    var onClickListener : View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.cell_dishes, parent, false)
        view.setOnClickListener(onClickListener)
        return DishViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    override fun onBindViewHolder(holder: DishViewHolder?, position: Int) {

        val dishName = dishes[position].name
        val dishCategory = dishes[position].category.toString()
        val dishImage = dishes[position].image
        val dishPrice = dishes[position].price

        holder?.bindTable(dishImage, dishName, dishCategory, dishPrice)
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