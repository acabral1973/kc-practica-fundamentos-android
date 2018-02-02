package es.smartech.foodr.models

import java.io.Serializable

data class OrderItem(var dish: Dish, var customerNotes: String ): Serializable{
}