package es.smartech.foodr.models

import java.io.Serializable

data class Restaurant(val name : String, val numberOfTables: Int, val menu: MutableList<Dish>, val tables: MutableList<Table>) : Serializable {
}