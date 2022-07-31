package com.edolo.aplicacionlibro.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edolo.aplicacionlibro.R
import com.edolo.aplicacionlibro.modelo.DataBooks
import com.edolo.aplicacionlibro.databinding.ActivityDetalleLibroBinding
import com.edolo.aplicacionlibro.modelo.LibroData

class DetalleLibroActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetalleLibroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleLibroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Informacion Libro"
        }

        var datos = intent.getSerializableExtra(resources.getString(R.string.identificador))
        datos as LibroData

        datos.imagen.toInt()?.let { binding.imvPortada.setImageResource(it) }
        binding.txtTitulo.text = datos.titulo
        binding.txtAutor.text = datos.autor
        binding.txtPrecio.text = datos.precio
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}