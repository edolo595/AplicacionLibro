package com.edolo.aplicacionlibro.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edolo.aplicacionlibro.R
import com.edolo.aplicacionlibro.data.controler.CrudBooks
import com.edolo.aplicacionlibro.modelo.DataBooks
import com.edolo.aplicacionlibro.databinding.ActivityEditarLibroBinding
import com.edolo.aplicacionlibro.modelo.LibroData

class   EditarLIbroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditarLibroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarLibroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var actionBar = getSupportActionBar()
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()?.setTitle("Editar libro")
        }
        /**Recibimos los parametros del libro*/
        var pos =intent.getLongExtra("ID_LIBRO",0)
        var datos = intent.getSerializableExtra("DATOS_LIBROS")
        datos as LibroData
        binding.etxEditTitulo.setText(datos.titulo)
        binding.etxEditAutor.setText(datos.autor)
        binding.etxEditGener.setText(datos.genero)
        binding.etxEditPrecio.setText(datos.precio)
        /**Creamos el evento click del boton aceptar*/
        binding.btnEditAceptar.setOnClickListener {
            val intento = Intent()
            if (textContenido()) {
                /* intento.putExtra(resources.getString(R.string.identificador_envio), "")
                 intento.putExtra(
                     resources.getString(R.string.id_libros_agregar),
                     DataBooks(
                         binding.etxEditTitulo.text.toString(),
                         binding.etxEditAutor.text.toString(),
                         binding.etxEditGener.text.toString(),
                         binding.etxEditPrecio.text.toString(),
                         0
                     )
                 )*/
                //setResult(Activity.RESULT_OK, intento)
                if (CrudBooks(this).actualizarLibro(pos,
                        LibroData(
                                pos,
                                binding.etxEditTitulo.text.toString(),
                                binding.etxEditAutor.text.toString(),
                                binding.etxEditGener.text.toString(),
                                binding.etxEditPrecio.text.toString(),
                                ""
                            )
                        ) != 0
                ) {
                    setResult(Activity.RESULT_OK)
                } else {
                    setResult(Activity.RESULT_CANCELED)
                }

                finish()
            } else {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }

    }

    private fun textContenido(): Boolean {
        if (binding.etxEditTitulo.text.isBlank()) {
            return false
        }
        if (binding.etxEditAutor.text.isBlank()) {
            return false
        }
        if (binding.etxEditGener.text.isBlank()) {
            return false
        }
        if (binding.etxEditPrecio.text.isBlank()) {
            return false
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}