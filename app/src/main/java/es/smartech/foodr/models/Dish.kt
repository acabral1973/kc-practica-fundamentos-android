package es.smartech.foodr.models

import java.io.Serializable

data class Dish (val name: String, val description: String, val price: Float, val image: Int, val allergens: MutableList<Allergen> = mutableListOf<Allergen>(), val category: Category) : Serializable {

    fun getPriceSting() = "Precio: ${price} €"
}



