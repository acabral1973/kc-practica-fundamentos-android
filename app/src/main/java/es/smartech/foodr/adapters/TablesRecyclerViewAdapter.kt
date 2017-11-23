package es.smartech.foodr.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import es.smartech.foodr.R

class TablesRecyclerViewAdapter(val numberOfTables: Int) : RecyclerView.Adapter<TablesRecyclerViewAdapter.TableViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TableViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.cell_tables, parent, false)
        return TableViewHolder(view)
    }

    override fun getItemCount(): Int {
        return numberOfTables
    }

    override fun onBindViewHolder(holder: TableViewHolder?, position: Int) {
        holder?.bindTable(position)
    }

    inner class TableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cellTablesText = itemView.findViewById<TextView>(R.id.cellTablesText)

        fun bindTable(tableNumber: Int) {
            cellTablesText.text = "Mesa ${tableNumber+1}"
        }
    }
}