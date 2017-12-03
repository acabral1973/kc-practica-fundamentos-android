package es.smartech.foodr.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import es.smartech.foodr.R
import es.smartech.foodr.models.Restaurant
import es.smartech.foodr.models.Table

class TablesRecyclerViewAdapter(val restaurant: Restaurant) : RecyclerView.Adapter<TablesRecyclerViewAdapter.TableViewHolder>() {

    var onClickListener : View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TableViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.cell_tables, parent, false)
        view.setOnClickListener(onClickListener)
        return TableViewHolder(view)
    }

    override fun getItemCount(): Int {
        return restaurant.tables.size
    }

    override fun onBindViewHolder(holder: TableViewHolder?, position: Int) {
        holder?.bindTable(position)
    }

    inner class TableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val idTable = itemView.findViewById<TextView>(R.id.table_id)
        val statusTable = itemView.findViewById<TextView>(R.id.table_status)
        val totalTable = itemView.findViewById<TextView>(R.id.table_total)

        @SuppressLint("ResourceAsColor")
        fun bindTable(tableNumber: Int) {

            idTable.text = "Mesa ${tableNumber+1}"

            totalTable.text = "${restaurant.tables[tableNumber].getPriceString()}"

            val statusTableTextColor = if (restaurant.tables[tableNumber].isOccupied()) {R.color.red} else {R.color.colorAccent}
            statusTable.setTextColor(statusTableTextColor)
            statusTable.text = "${restaurant.tables[tableNumber].getOcupationString()}"
        }
    }
}