package es.smartech.foodr.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import es.smartech.foodr.R
import es.smartech.foodr.fragments.DishesFragment
import es.smartech.foodr.models.Dish
import es.smartech.foodr.models.Restaurant

class AddDishActivity : AppCompatActivity(), DishesFragment.OnFragmentAddDishListener {

    companion object {

        private val EXTRA_RESTAURANT = "EXTRA_RESTAURANT"
        val EXTRA_DISH_ADDED = "EXTRA_DISH_ADDED"
        val EXTRA_NOTES_ADDED = "EXTRA_NOTES_ADDED"

        fun intent(context: Context, restaurant: Restaurant) : Intent {
            val intent = Intent(context, AddDishActivity::class.java)
            intent.putExtra(EXTRA_RESTAURANT, restaurant)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_dish)
        val restaurant = intent.getSerializableExtra(AddDishActivity.EXTRA_RESTAURANT) as Restaurant

        supportActionBar?.title = "Seleccione un plato"

        // cargo el fragmento de listado de platos en modo add
        if (fragmentManager.findFragmentById(R.id.order_fragment_container) == null) {
            fragmentManager.
                    beginTransaction().
                    replace(R.id.order_fragment_container, DishesFragment.newInstance(restaurant, true))
                    .commit()
        }

    }

    override fun dishIsAdded(dish: Dish, customerNotes: String) {
        // cuando DishesFragment me devuelve un plato que se ha agregado, lo paso al OrderManagerActivity
        val returnIntent = Intent()
        returnIntent.putExtra(EXTRA_DISH_ADDED, dish)
        returnIntent.putExtra(EXTRA_NOTES_ADDED, customerNotes)
        setResult(Activity.RESULT_OK, returnIntent)
        // Finalizamos esta actividad, regresando a la anterior
        finish()
    }

}
