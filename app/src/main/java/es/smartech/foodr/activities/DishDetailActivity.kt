package es.smartech.foodr.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import es.smartech.foodr.R
import es.smartech.foodr.models.Dish
import kotlinx.android.synthetic.main.activity_dish_detail.*

class DishDetailActivity : AppCompatActivity() {

    companion object {

        private val EXTRA_DISH = "EXTRA_DISH"

        fun intent(context: Context, dish: Dish) : Intent {
            val intent = Intent(context, DishDetailActivity::class.java)
            intent.putExtra(EXTRA_DISH, dish)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_detail)

        val dish = intent.getSerializableExtra(EXTRA_DISH) as Dish

        dish_image.setImageResource(dish.image)
        dish_name.text = dish.name
        dish_category.text = "${(dish.category)}"
        dish_price.text = dish.getPriceSting()
        dish_description.text = dish.description
    }
}
