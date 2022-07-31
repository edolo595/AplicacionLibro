package com.edolo.aplicacionlibro

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.edolo.aplicacionlibro.activity.AgregarLibroActivity
import com.edolo.aplicacionlibro.activity.AplicacionLibro
import com.edolo.aplicacionlibro.activity.DetalleLibroActivity
import com.edolo.aplicacionlibro.activity.EditarLIbroActivity
import com.edolo.aplicacionlibro.adapter.AdapterListVew
import com.edolo.aplicacionlibro.modelo.DataBooks
import com.edolo.aplicacionlibro.data.ProviderBooks
import com.edolo.aplicacionlibro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: ActivityMainBinding
    lateinit var adaptador: AdapterListVew
    private var pushDetalleLibro = false
    private var pushAgregarLibro = false
    private lateinit var dialogo: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialogo = AlertDialog.Builder(this)

        adaptador = AdapterListVew(this, ProviderBooks.dataBooks)

        binding.listBooks.adapter = adaptador
        binding.listBooks.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, "preciono en $i", Toast.LENGTH_SHORT).show()
            //enviarDatos(ReceptorActivity())
            val intento: Intent = Intent(this, DetalleLibroActivity::class.java)
            intento.putExtra(
                resources.getString(R.string.identificador),
                ProviderBooks.dataBooks[i]
            )
            if (!pushDetalleLibro) {
                startActivity(intento)
                pushDetalleLibro = true
            }


        }

        binding.agregarLibro.setOnClickListener {
            val intento: Intent = Intent(this, AgregarLibroActivity::class.java)
            //startActivity(intento)
            //Toast.makeText(this,"Preiono agregar",Toast.LENGTH_LONG).show()
            if (!pushAgregarLibro) {
                resultadoAgregar.launch(intento)
                pushAgregarLibro = true
            }

        }


        registerForContextMenu(binding.listBooks)
        binding.agregarLibro.setOnLongClickListener {
            mostrarMenuEmergente(it)
            true
        }
        comprobarColor()

    }

    override fun onResume() {
        pushDetalleLibro = false
        super.onResume()
    }

   private var resultadoAgregar =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == Activity.RESULT_OK) {
                val mens =
                    resultado.data?.getSerializableExtra(resources.getString(R.string.id_libros_agregar))
                        ?: ""
                Toast.makeText(this, "GREGADO EXITOSAMENTE", Toast.LENGTH_SHORT).show()
                mens as DataBooks
                println("Datos titulo:${mens.titulo}")
                pushAgregarLibro = false
            }
            if (resultado.resultCode == Activity.RESULT_CANCELED) {
                pushAgregarLibro = false
                Toast.makeText(this, "CAMPOS VACIOS", Toast.LENGTH_SHORT).show()
            }
        }
    var resultadoEditarLIbro =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == Activity.RESULT_OK) {
                val mens =
                    resultado.data?.getSerializableExtra(resources.getString(R.string.id_libros_agregar))
                        ?: ""
                Toast.makeText(this, "EDITADO EXITOSAMENTE", Toast.LENGTH_SHORT).show()
                mens as DataBooks
                println("Datos titulo:${mens.titulo}")

                ProviderBooks.dataBooks.set(
                    posisionEditada!!,
                    DataBooks(
                        mens.titulo,
                        mens.autor,
                        mens.genero,
                        mens.precio,
                        ProviderBooks.dataBooks[posisionEditada!!].imagen
                    )
                )
                adaptador.notifyDataSetChanged()
            }
            if (resultado.resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "CAMPOS VACIOS", Toast.LENGTH_SHORT).show()
            }
        }


    /**
     * funcion de sobreecritura para crear un menu en el toolBar
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflador: MenuInflater = menuInflater
        inflador.inflate(R.menu.menu_opciones, menu)
        return true
    }

    /**
     * funcion de sobreecritura para escuchar cuando se selecciona un buttom
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.bt_ayuda -> {
                Toast.makeText(this, "ayuda", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.bt_config -> {
                Toast.makeText(this, "opcion", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.bt_salir -> {
                dialogo.setTitle("La aplicacion se cerrara")
                    .setMessage("¿Estas seguro?")
                    .setCancelable(true)
                    .setPositiveButton("Si") { dialIter, it ->
                        finish()
                    }
                    .setNegativeButton("No") { dialIter, it ->
                        dialIter.cancel()
                    }.show()

                Toast.makeText(this, "salir", Toast.LENGTH_SHORT).show()
                // finish()
                true
            }
            R.id.bt_agregar_libro -> {
                val intento: Intent = Intent(this, AgregarLibroActivity::class.java)
                if (!pushAgregarLibro) {
                    resultadoAgregar.launch(intento)
                    pushAgregarLibro = true
                }
                Toast.makeText(this, "agregar libro", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    /**
     * funcion de sobreecritura para crear un menu contextual
     */
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        var inflador = menuInflater
        inflador.inflate(R.menu.menu_contextual, menu)
    }

    /**Menu contextual botones eliminar y actualizar*/
    var posisionEditada: Int? = null
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        posisionEditada = info.position
        return when (item.itemId) {
            R.id.id_eliminar -> {

                Toast.makeText(this, "Boton Elimina", Toast.LENGTH_SHORT).show()
                ProviderBooks.dataBooks.remove(ProviderBooks.dataBooks[info.position])
                adaptador.notifyDataSetChanged()
                // data.remove(data[info.position])

                true
            }
            R.id.id_editar -> {

                val intento: Intent = Intent(this, EditarLIbroActivity::class.java)
                intento.putExtra(
                    resources.getString(R.string.identificador),
                    ProviderBooks.dataBooks[posisionEditada!!]
                )
                resultadoEditarLIbro.launch(intento)
                Toast.makeText(this, "Boton Editar", Toast.LENGTH_SHORT).show()
                true

            }
            else -> false
        }
    }


    /**
     * Funcion para mostrara el menu emergente de tipo Popup
     */
    private fun mostrarMenuEmergente(vista: View) {
        val emergente = PopupMenu(this, vista)
        emergente.setOnMenuItemClickListener(this)
        val inflador = emergente.menuInflater
        inflador.inflate(R.menu.menu_emergente, emergente.menu)
        emergente.show()
    }

    /**
     * Evento cclick para los items del evento Popup
     */
    var estadoColorBtn = 0
    var estadoIconBtn = 0
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.bt_color -> {
                if (estadoColorBtn == 0) {

                    binding.agregarLibro.setBackgroundTintList(
                        ContextCompat.getColorStateList(
                            this,
                            R.color.secondaryColor
                        )
                    )
                    AplicacionLibro.prefs.guardarColor(R.color.secondaryColor)
                    estadoColorBtn = 1
                } else {
                    binding.agregarLibro.setBackgroundTintList(
                        ContextCompat.getColorStateList(
                            this,
                            R.color.primaryColor
                        )
                    )
                    AplicacionLibro.prefs.guardarColor(R.color.primaryColor)
                    estadoColorBtn = 0
                }

                Toast.makeText(this, "Cambiar color", Toast.LENGTH_SHORT).show()

                true
            }
            R.id.bt_texto -> {
                if (estadoIconBtn == 0) {
                    binding.agregarLibro.imageTintList = getColorStateList(R.color.black)
                    estadoIconBtn = 1
                } else {
                    binding.agregarLibro.imageTintList = getColorStateList(R.color.white)
                    estadoIconBtn = 0
                }

                Toast.makeText(this, "Cambiar text", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.bt_desabilitar -> {
                binding.agregarLibro.isVisible = false
                Toast.makeText(this, "Desabilitar", Toast.LENGTH_SHORT).show()
                true
            }
            else -> false
        }

    }

    private fun comprobarColor(){
        println("Esto es el color:"+AplicacionLibro.prefs.getColor())
        if(AplicacionLibro.prefs.getColor() != 0){

            binding.agregarLibro.setBackgroundTintList(
                ContextCompat.getColorStateList(
                    this,
                    AplicacionLibro.prefs.getColor()
                )
            )
        }
    }

}


