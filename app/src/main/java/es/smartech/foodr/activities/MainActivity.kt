package es.smartech.foodr.activities

import android.app.AlertDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.internal.NavigationMenu
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.util.Log
import android.view.View
import es.smartech.foodr.CONSTANT_URL_DESCARGA
import es.smartech.foodr.R
import es.smartech.foodr.fragments.DishesFragment
import es.smartech.foodr.fragments.TablesFragment
import es.smartech.foodr.models.Allergen
import es.smartech.foodr.models.Category
import es.smartech.foodr.models.Dish
import es.smartech.foodr.models.Restaurant
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

    val NAVIGATION_MENU_DEFAULT_OPTION = R.id.action_tables
    val DEFAULT_FRAGMENT = getFragmentToShow(NAVIGATION_MENU_DEFAULT_OPTION)

    var restaurantData : Restaurant? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configuro la Toolbar
        setSupportActionBar(mainToolbar)

        // Si no tengo datos los actualizo desde Internet
        if (restaurantData == null) {
            updateData()
        }

        // Configuro el listener que gestionará las selecciones sobre el Navigation Menu
        navigation_view.setOnNavigationItemSelectedListener { item ->
            val selectedItem = item.itemId
            val fragmentToShow = getFragmentToShow(selectedItem)
            replaceFragment(fragmentToShow)

            true
        }
    }

    fun getFragmentToShow(itemId : Int) : Fragment {
        return if (itemId == R.id.action_dishes) {
            DishesFragment()
        }
        else {
            TablesFragment.newInstance(restaurantData)
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
    }

    fun initView() {

        // Recupero el fragmento actual
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        // si no hay ningun fragmento actual (estoy cargando la vista por primera vez), creo el fragmento que esté seteado por defecto (NAVIGATION_MENU_DEFAULT_OPTION)
        if (currentFragment == null) {

            navigation_view.selectedItemId = NAVIGATION_MENU_DEFAULT_OPTION
            replaceFragment(DEFAULT_FRAGMENT)
        }
    }


    private fun updateData() {
        view_switcher.displayedChild = VIEW_INDEX.DOWNLOADING.index
        async(UI) {
            val data : Deferred<Restaurant?> = bg {
                downloadData()
            }

            val downloadedData = data.await()

            if (downloadedData != null) {
                // La descarga se ha realizado correctamente
                view_switcher.displayedChild = VIEW_INDEX.HOME_MENU.index
                restaurantData = downloadedData

                // Actualizo el título de la Toolbar
                supportActionBar?.title = "Foodr (${restaurantData?.name})"

                // Inicializamos la vista para que cargue el fragmento por defecto (TablesFragment)
                initView()
            }
            else {
                // La descarga ha acabado con error
                AlertDialog.Builder(this@MainActivity)
                        .setTitle("Error")
                        .setMessage("Error al descargar datos desde el servidor. Por favor verifica la conexión de tu teminal")
                        .setPositiveButton("Reintentar", { dialog, _ ->
                            dialog.dismiss()
                            updateData()
                        })
                        .setNegativeButton("Salir", { _, _ -> finish() })
                        .show()
            }
        }
    }

    fun downloadData(): Restaurant? {
        try {
            // Retardo simulado
            Thread.sleep(3000)

            // Descargamos los platos desde el servicio web
            val url = URL(CONSTANT_URL_DESCARGA)
            val jsonString = Scanner(url.openStream(), "UTF-8").useDelimiter("\\A").next()

            // Recuperamos nombre, cantidad de mesas y lista de platos del JSON recibido
            val jsonRoot = JSONObject(jsonString)
            val downloadedDishesList = jsonRoot.getJSONArray("dishes")
            val downloadedName = jsonRoot.getString("name")
            val downloadedTables = jsonRoot.getDouble("tables").toInt()

            // Creamos una lista de platos
            val dishesList = mutableListOf<Dish>()
            for (dishIndex in 0 until downloadedDishesList.length()) {

                Log.v("TAG", "Importando plato @{dishIndex}")

                val dish = downloadedDishesList.getJSONObject(dishIndex)
                val dishName = dish.getString("name")
                val dishDescription = dish.getString("description")
                val dishprice = dish.getDouble("price").toFloat()
                val dishImageString = dish.getString("image")
                val dishCategory = Category.valueOf(dish.getString("category"))

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

                dishesList.add(Dish(dishName, dishDescription, dishprice, dishImage, dishAllergens, dishCategory))
            }

            return Restaurant(downloadedName, downloadedTables, dishesList)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return null
    }


}
