package es.smartech.foodr.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import es.smartech.foodr.R
import es.smartech.foodr.activities.DishDetailActivity.Companion.EXTRA_DISH
import es.smartech.foodr.models.Allergen
import es.smartech.foodr.models.Dish
import kotlinx.android.synthetic.main.activity_dish_detail.*

class DishDetailActivity : AppCompatActivity() {

    companion object {

        val EXTRA_DISH = "EXTRA_DISH"
        val EXTRA_NOTES = "EXTRA_NOTES"

        private val EXTRA_ACTIVITY_MODE = "EXTRA_ACTIVITY_MODE"

        fun intent(context: Context, dish: Dish, addMode: Boolean) : Intent {
            val intent = Intent(context, DishDetailActivity::class.java)
            intent.putExtra(EXTRA_DISH, dish)
            intent.putExtra(EXTRA_ACTIVITY_MODE, addMode)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_detail)

        val dish = intent.getSerializableExtra(EXTRA_DISH) as Dish
        val addMode = intent.getBooleanExtra(EXTRA_ACTIVITY_MODE, false)

        supportActionBar?.title = "${dish?.name} (Detalles)"
        dish_image.setImageResource(dish.image)
        dish_name.text = dish.name
        dish_category.text = "${(dish.category)}"
        dish_price.text = dish.getPriceSting()
        dish_description.text = dish.description

        dish.allergens.forEach {
            when (it) {
                Allergen.EGG -> eggs_icon.setImageResource(R.drawable.icon_eggs_green)
                Allergen.NUTS -> nut_icon.setImageResource(R.drawable.icon_peanuts_green)
                Allergen.GLUTEN -> gluten_icon.setImageResource(R.drawable.icon_gluten_green)
                Allergen.LACTOSE -> milk_icon.setImageResource(R.drawable.icon_milk_green)
                Allergen.SHELLFISH -> crab_icon.setImageResource(R.drawable.icon_crab_green)
            }
        }

        if  (addMode) {
            add_button.isEnabled = true
            add_button.visibility = View.VISIBLE
            notas_edit_text.isEnabled = true
            notas_edit_text.visibility = View.VISIBLE
            label_notas_cliente.isEnabled = true
            label_notas_cliente.visibility = View.VISIBLE

            add_button.setOnClickListener { addDish(dish, notas_edit_text.text.toString()) }

        } else {
            add_button.isEnabled = false
            add_button.visibility = View.INVISIBLE
            notas_edit_text.isEnabled = false
            notas_edit_text.visibility = View.INVISIBLE
            label_notas_cliente.isEnabled = false
            label_notas_cliente.visibility = View.INVISIBLE
        }
    }

    private fun addDish(dish: Dish, customerNotes: String) {
        val returnIntent = Intent()
        returnIntent.putExtra(EXTRA_DISH, dish)
        returnIntent.putExtra(EXTRA_NOTES, customerNotes)
        setResult(Activity.RESULT_OK, returnIntent)
        // Finalizamos esta actividad, regresando a la anterior
        finish()
    }
}
