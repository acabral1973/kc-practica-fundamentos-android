package es.smartech.foodr.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import es.smartech.foodr.R
import es.smartech.foodr.models.Restaurant
import kotlinx.android.synthetic.main.activity_table_order.*

class TableOrderActivity : AppCompatActivity() {

    companion object {

        private val EXTRA_RESTAURANT = "EXTRA_RESTAURANT"
        private val EXTRA_TABLE_NUMBER = "EXTRA_TABLE_NUMBER"

        fun intent(context: Context, restaurant: Restaurant, tableNumber: Int) : Intent {
            val intent = Intent(context, TableOrderActivity::class.java)
            intent.putExtra(EXTRA_RESTAURANT, restaurant)
            intent.putExtra(EXTRA_TABLE_NUMBER, tableNumber)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_order)

        val restaurant = intent.getSerializableExtra(TableOrderActivity.EXTRA_RESTAURANT) as Restaurant
        val tableNumber = intent.getIntExtra(TableOrderActivity.EXTRA_TABLE_NUMBER,0)

        supportActionBar?.title = "Pedido mesa ${tableNumber+1}"
    }
}
