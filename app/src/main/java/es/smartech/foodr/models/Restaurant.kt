package es.smartech.foodr.models

import java.io.Serializable

class Restaurant(val name : String, val numberOfTables: Int, val menu: List<Dish>, val tables: List<Table>) : Serializable {
}