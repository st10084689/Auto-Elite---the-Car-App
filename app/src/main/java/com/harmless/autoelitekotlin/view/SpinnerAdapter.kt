package com.harmless.autoelitekotlin.view


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.harmless.autoelitekotlin.*

class SpinnerAdapter(context: Context, items: List<String>) : ArrayAdapter<String>(context, R.layout.custom_spinner, items) {

    private val context: Context = context
    private val items: List<String> = items

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            itemView = inflater.inflate(R.layout.custom_spinner, parent, false)
        }

        val textViewItem: TextView = itemView!!.findViewById(R.id.text1)
        textViewItem.text = items[position]

        return itemView
    }
}