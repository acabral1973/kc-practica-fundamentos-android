package es.smartech.foodr.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import es.smartech.foodr.R
import es.smartech.foodr.activities.AddDishActivity.Companion.EXTRA_DISH_ADDED
import es.smartech.foodr.fragments.DishesFragment
import es.smartech.foodr.fragments.OrderFragment
import es.smartech.foodr.models.Restaurant
import es.smartech.foodr.models.Table
import kotlinx.android.synthetic.main.activity_close_table.*

class CloseTableActivity : AppCompatActivity() {

    companion object {
        val EXTRA_RESTAURANT = "EXTRA_RESTAURANT"
        val EXTRA_TABLE_NUMBER = "EXTRA_TABLE_NUMBER"
        val CLOSE_TABLE = "CLOSE_TABLE"

        fun intent(context: Context, restaurant: Restaurant, tableNumber: Int) : Intent {
            val intent = Intent(context, CloseTableActivity::class.java)
            intent.putExtra(EXTRA_RESTAURANT, restaurant)
            intent.putExtra(EXTRA_TABLE_NUMBER, tableNumber)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_close_table)
        val restaurant = intent.getSerializableExtra(CloseTableActivity.EXTRA_RESTAURANT) as Restaurant
        val tableNumber = intent.getIntExtra(CloseTableActivity.EXTRA_TABLE_NUMBER,0)
        val orderGrandTotal = restaurant.tables[tableNumber].price

        supportActionBar?.title = "Cuenta de la mesa ${tableNumber+1}"

        // cargo el OrderFragment correspondiente a la mesa
        if (fragmentManager.findFragmentById(R.id.order_fragment_container) == null) {
            fragmentManager.beginTransaction().replace(R.id.order_fragment_container, OrderFragment.newInstance(restaurant, tableNumber))
                    .commit()
        }

        // Cargo el importe total de la orden de esta mesa
        text_total.text= "${orderGrandTotal} €"

        //configuro el OnClickListnr del botón de cerrar cuenta de la mesa seleccionada
        charge_button.setOnClickListener {
            // libero la mesa
            restaurant.tables[tableNumber].setTableFree()

            // devuelvo el restaurante con la mesa liberada
            val returnIntent = Intent()
            returnIntent.putExtra(EXTRA_RESTAURANT, restaurant)
            setResult(Activity.RESULT_OK, returnIntent)
            // Finalizamos esta actividad, regresando a la anterior
            finish()
        }
    }
}
