package es.smartech.foodr.models

import java.io.Serializable

data class Table (var number: Int, var occupied: Boolean, var price: Float, var order: MutableList<OrderItem> = mutableListOf<OrderItem>()) : Serializable {

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

    fun addDishToOrder(dish: Dish, customerNotes: String) {
        val newDishToOrder = OrderItem(dish, customerNotes)
        order.add(newDishToOrder)
        price = price + dish.price
        setTableOccupied()
    }
}

