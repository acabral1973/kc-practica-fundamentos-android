package es.smartech.foodr.models

import java.io.Serializable

data class Table (var number: Int, var occupied: Boolean, var price: Float, var order: List<Dish>?) : Serializable {

    fun getPriceString() = "Importe: ${price} €"

    fun setTableFree() {
        occupied = false
        price = 0f
        order = null
    }

    fun setTableOccupied() {
        occupied = true
    }

    fun getOcupationString() = if (occupied) {"ocupada"} else {"libre"}

    fun isOccupied() = occupied
}

