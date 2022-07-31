package com.edolo.aplicacionlibro.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edolo.aplicacionlibro.R
import com.edolo.aplicacionlibro.adapter.AdapterListVew
import com.edolo.aplicacionlibro.data.controler.CrudBooks
import com.edolo.aplicacionlibro.modelo.DataBooks
import com.edolo.aplicacionlibro.databinding.ActivityAgregarLibroBinding
import com.edolo.aplicacionlibro.modelo.LibroData

class AgregarLibroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgregarLibroBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarLibroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var actionBar = getSupportActionBar()
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()?.setTitle("Nuevo Libro")
        }



        binding.btnAceptar.setOnClickListener {
            val intento = Intent()

            if (textContenido()) {
                intento.putExtra(resources.getString(R.string.identificador_envio), "")
                intento.putExtra(
                    resources.getString(R.string.id_libros_agregar),
                    CrudBooks(this).gregarLibro(
                        LibroData(
                            0,
                            binding.etxTitulo.text.toString(),
                            binding.etxAutor.text.toString(),
                            binding.etxGener.text.toString(),
                            binding.etxPrecio.text.toString(),
                            R.drawable.p1.toString()
                        )
                    )
                )
                setResult(Activity.RESULT_OK, intento)
                finish()
            } else {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }

    private fun textContenido(): Boolean {
        if (binding.etxTitulo.text.isBlank()) {
            return false
        }
        if (binding.etxAutor.text.isBlank()) {
            return false
        }
        if (binding.etxGener.text.isBlank()) {
            return false
        }
        if (binding.etxPrecio.text.isBlank()) {
            return false
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


/*Cerrar actividad por medio de un item
    override fun onContextItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                android.R.id.home -> {
                    finish()
                    return true
                }
            }
            return super.onContextItemSelected(item)
    }*/
}