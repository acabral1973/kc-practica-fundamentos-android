package es.smartech.foodr.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import es.smartech.foodr.R
import es.smartech.foodr.fragments.OrderFragment
import es.smartech.foodr.models.Dish
import es.smartech.foodr.models.Restaurant
import kotlinx.android.synthetic.main.activity_order_manager.*


class OrderManagerActivity : AppCompatActivity() {

    enum class VIEW_INDEX(val index: Int) {
        NO_ORDER(0),
        ORDER(1)
    }

    companion object {
        val REQUEST_ADD_DISH = 1
        val REQUEST_CLOSE_TABLE = 2
        val EXTRA_RESTAURANT = "EXTRA_RESTAURANT"
        val EXTRA_TABLE_NUMBER = "EXTRA_TABLE_NUMBER"

        fun intent(context: Context, restaurant: Restaurant, tableNumber: Int) : Intent {
            val intent = Intent(context, OrderManagerActivity::class.java)
            intent.putExtra(EXTRA_RESTAURANT, restaurant)
            intent.putExtra(EXTRA_TABLE_NUMBER, tableNumber)
            return intent
        }
    }

    lateinit var restaurant : Restaurant
    var tableNumber : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_manager)
        restaurant = intent.getSerializableExtra(OrderManagerActivity.EXTRA_RESTAURANT) as Restaurant
        tableNumber = intent.getIntExtra(OrderManagerActivity.EXTRA_TABLE_NUMBER,0)

        supportActionBar?.title = "Pedido mesa ${tableNumber+1}"

        // cargo el fragmento con la orden de la mesa seleccionada
        if (fragmentManager.findFragmentById(R.id.order_fragment_container) == null) {
            fragmentManager.beginTransaction().replace(R.id.order_fragment_container, OrderFragment.newInstance(restaurant, tableNumber)).commit()
    }

        //configuro el OnClickListnr del botón de agregar platos para que lance la actividad de agregar platos
        add_button.setOnClickListener {
            startActivityForResult(AddDishActivity.intent(this, restaurant), REQUEST_ADD_DISH)
        }

        //configuro el OnClickListnr del botón lanzar el pedido
        order_button.setOnClickListener {
            // cuando cierro pedido de mesa devuelvo el pedido a la actividad principal
            val returnIntent = Intent()
            returnIntent.putExtra(EXTRA_RESTAURANT, restaurant)
            setResult(Activity.RESULT_OK, returnIntent)
            // Finalizamos esta actividad, regresando a la anterior
            finish()
        }

        //configuro el OnClickListnr del botón de cerrar cuenta de la mesa seleccionada
        close_button.setOnClickListener {
            startActivityForResult(CloseTableActivity.intent(this, restaurant, tableNumber), REQUEST_CLOSE_TABLE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ADD_DISH) {
            if (resultCode == Activity.RESULT_OK) {
                val dish = data?.getSerializableExtra(AddDishActivity.EXTRA_DISH_ADDED) as Dish
                restaurant.tables[tableNumber].addDishToOrder(dish)
                val orderFragment = fragmentManager.findFragmentById(R.id.order_fragment_container) as? OrderFragment
                orderFragment?.updateOrder(restaurant)
            }
        }

        if (requestCode == REQUEST_CLOSE_TABLE) {
            if (resultCode == Activity.RESULT_OK) {
                restaurant = data?.getSerializableExtra(CloseTableActivity.EXTRA_RESTAURANT) as Restaurant

                // devuelvo el restaurante con la mesa liberada
                val returnIntent = Intent()
                returnIntent.putExtra(OrderManagerActivity.EXTRA_RESTAURANT, restaurant)
                setResult(Activity.RESULT_OK, returnIntent)
                // Finalizamos esta actividad, regresando a la anterior
                finish()
            }
        }


    }

}
