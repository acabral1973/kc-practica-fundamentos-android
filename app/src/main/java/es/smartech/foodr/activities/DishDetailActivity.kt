package es.smartech.foodr.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import es.smartech.foodr.R
import es.smartech.foodr.models.Dish
import kotlinx.android.synthetic.main.activity_dish_detail.*

class DishDetailActivity : AppCompatActivity() {

    companion object {

        enum class ACTIVITY_MODE {
            TO_SHOW_DISH,
            TO_ORDER_DISH
        }

        private val EXTRA_DISH = "EXTRA_DISH"
        private val EXTRA_ACTIVITY_MODE = "EXTRA_ACTIVITY_MODE"

        fun intent(context: Context, dish: Dish, activityMode: ACTIVITY_MODE) : Intent {
            val intent = Intent(context, DishDetailActivity::class.java)
            intent.putExtra(EXTRA_DISH, dish)
            intent.putExtra(EXTRA_ACTIVITY_MODE, activityMode)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_detail)

        val dish = intent.getSerializableExtra(EXTRA_DISH) as Dish
        val activityMode = intent.getSerializableExtra(EXTRA_ACTIVITY_MODE) as ACTIVITY_MODE

        if  (activityMode == ACTIVITY_MODE.TO_ORDER_DISH) {
            add_button.isEnabled = true
            add_button.visibility = View.VISIBLE
        } else {
            add_button.isEnabled = false
            add_button.visibility = View.INVISIBLE
        }

        supportActionBar?.title = "${dish?.name} (Detalles)"
        dish_image.setImageResource(dish.image)
        dish_name.text = dish.name
        dish_category.text = "${(dish.category)}"
        dish_price.text = dish.getPriceSting()
        dish_description.text = dish.description
    }
}
