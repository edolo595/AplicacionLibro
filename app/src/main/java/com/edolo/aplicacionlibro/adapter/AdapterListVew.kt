package com.edolo.aplicacionlibro.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.edolo.aplicacionlibro.modelo.DataBooks
import com.edolo.aplicacionlibro.databinding.ItemsBookBinding


class AdapterListVew(
    var contexto: Context,
    var items: List<DataBooks> = arrayListOf<DataBooks>()
) : ArrayAdapter<DataBooks>(contexto, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemsBookBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.imgPortada.setImageDrawable(items[position].imagen?.let { context.getDrawable(it) })
        binding.txtTitulo.text = items[position].titulo
        binding.txtAutor.text = items[position].autor
        binding.txtGenero.text = items[position].genero
        binding.txtPrecio.text = items[position].precio

        return binding.root
    }

    override fun getCount(): Int {
        return items.size
    }
}