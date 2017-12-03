package es.smartech.foodr.models

import java.io.Serializable

data class Table (var number: Int, var occupied: Boolean, var price: Float, var order: MutableList<Dish> = mutableListOf<Dish>()) : Serializable {

    fun getPriceString() = "Importe: ${price} €"

    fun setTableFree() {
        occupied = false
        price = 0f
        order.clear()
    }

    fun setTableOccupied() {
        occupied = true
    }

    fun getOcupationString() = if (occupied) {"Ocupada"} else {"Libre"}

    fun isOccupied() = occupied

    fun addDishToOrder(dish: Dish) {
        order.add(dish)
        price = price + dish.price
        setTableOccupied()
    }
}

