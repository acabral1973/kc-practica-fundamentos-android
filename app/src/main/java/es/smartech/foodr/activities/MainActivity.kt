package es.smartech.foodr.activities

import android.app.AlertDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ViewSwitcher
import es.smartech.foodr.CONSTANT_URL_DESCARGA
import es.smartech.foodr.R
import es.smartech.foodr.models.Allergen
import es.smartech.foodr.models.Dish
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.json.JSONObject
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {

    enum class VIEW_INDEX(val index: Int) {
        DOWNLOADING(0),
        HOME_MENU(1)
    }

    var dishes: List<Dish>? = null
        set(value) {
            field = value
            if (value != null) {

            } else {
                updateDishes()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateDishes()
    }

    private fun updateDishes() {
        view_switcher.displayedChild = VIEW_INDEX.DOWNLOADING.index
        async(UI) {
            val newDishes: Deferred<List<Dish>?> = bg {
                downloadDishes()
            }

            val downloadedDishes = newDishes.await()

            if (downloadedDishes != null) {
                // La descarga se ha realizado correctamente
                dishes = downloadedDishes
            }
            else {
                // La descarga ha acabado con error
                AlertDialog.Builder(this@MainActivity)
                        .setTitle("Error")
                        .setMessage("Error al descargar datos desde el servidor. Por favor verifica la conexión de tu teminal")
                        .setPositiveButton("Reintentar", { dialog, _ ->
                            dialog.dismiss()
                            updateDishes()
                        })
                        .setNegativeButton("Salir", { _, _ -> finish() })
                        .show()
            }
        }
    }

    fun downloadDishes(): List<Dish>? {
        try {
            // Retardo simulado
            Thread.sleep(3000)

            // Descargamos los platos desde el servicio web
            val url = URL(CONSTANT_URL_DESCARGA)
            val jsonString = Scanner(url.openStream(), "UTF-8").useDelimiter("\\A").next()

            // Recuperamos la lista de platos del JSON recibido
            val jsonRoot = JSONObject(jsonString)
            val downloadedDishesList = jsonRoot.getJSONArray("dishes")

            // Creamos una lista de platos
            val dishesList = mutableListOf<Dish>()
            for (dishIndex in 0 until downloadedDishesList.length()) {

                Log.v("TAG", "Importando plato @{dishIndex}")

                val dish = downloadedDishesList.getJSONObject(dishIndex)
                val dishName = dish.getString("name")
                val dishDescription = dish.getString("description")
                val dishprice = dish.getDouble("price").toFloat()
                val dishImageString = dish.getString("image")

                // Generamos lista de alergenos
                val downloadedAllergens = dish.getJSONArray("allergens")
                val dishAllergens = mutableListOf<Allergen>()
                for (allergenIndex in 0 until downloadedAllergens.length()) {
                    dishAllergens.add(Allergen.valueOf(downloadedAllergens[allergenIndex] as String))
                }

                // Recuperamos el drawable correspondiente a dishImageString
                val dishImage = when (dishImageString) {
                    "brownie" -> R.drawable.brownie
                    "cafe" -> R.drawable.cafe
                    "cerveza" -> R.drawable.cerveza
                    "croquetas" -> R.drawable.croquetas
                    "ensalada_cesar" -> R.drawable.ensalada_cesar
                    "ensalada_toscana" -> R.drawable.ensalada_toscana
                    "ensalada_veggie" -> R.drawable.ensalada_veggie
                    "entrecot" -> R.drawable.entrecot
                    "fingers_pollo" -> R.drawable.fingers_pollo
                    "fruta" -> R.drawable.fruta
                    "helados" -> R.drawable.helados
                    "lomos_merluza" -> R.drawable.lomos_merluza
                    "patatas_rustic" -> R.drawable.patatas_rustic
                    "pechuga_villaroy" -> R.drawable.pechuga_villaroy
                    "refrescos" -> R.drawable.refrescos
                    else -> R.drawable.image_not_available
                }

                dishesList.add(Dish(dishName, dishDescription, dishprice, dishImage, dishAllergens))
            }

            return dishesList

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return null
    }


}
