package com.edolo.aplicacionlibro

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.edolo.aplicacionlibro.activity.AgregarLibroActivity
import com.edolo.aplicacionlibro.activity.DetalleLibroActivity
import com.edolo.aplicacionlibro.activity.EditarLIbroActivity
import com.edolo.aplicacionlibro.adapter.AdapterListVew
import com.edolo.aplicacionlibro.adapter.AdapaterLibrosVew
import com.edolo.aplicacionlibro.data.ProviderBooks
import com.edolo.aplicacionlibro.data.controler.CrudBooks
import com.edolo.aplicacionlibro.databinding.ActivitySqlBookBinding
import com.edolo.aplicacionlibro.modelo.DataBooks
import com.edolo.aplicacionlibro.modelo.LibroData
import java.io.Serializable

class SqlBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySqlBookBinding
    private var lista = ArrayList<LibroData>()
    private var pushAgregarLibro = false
    lateinit var adaptador: AdapaterLibrosVew
    private var pushDetalleLibro =
        false/*booleano para evitar que se carguen mas de dos instancias de la nueva activity*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySqlBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        agregarLibroListVew()
        /**Agregamos un libro nueva a base de datos*/
        binding.agregarLibro.setOnClickListener {
            val intento: Intent = Intent(this, AgregarLibroActivity::class.java)
            //startActivity(intento)
            //Toast.makeText(this,"Preiono agregar",Toast.LENGTH_LONG).show()
            if (!pushAgregarLibro) {
                resultadoAgregar.launch(intento)
                pushAgregarLibro = true
            }

        }
        /**Click en item del libro para ver su detalle*/
        binding.listBooks.setOnItemClickListener { adapterView, view, it, l ->
            Toast.makeText(
                this,
                "preciono en $it",
                Toast.LENGTH_SHORT
            ).show()//mesaje que muestra la posision que se presiono
            //enviarDatos(ReceptorActivity())
            val intento: Intent = Intent(
                this,
                DetalleLibroActivity::class.java
            )//cargamos el intent conla clase que pasaos
            intento.putExtra(
                resources.getString(R.string.identificador),
                CrudBooks(applicationContext).leerLibro()[it]
            )
            if (!pushDetalleLibro) {
                startActivity(intento)
                pushDetalleLibro = true
            }
        }

        //agregamos los menus contextuales a los items de la lista
        registerForContextMenu(binding.listBooks)
    }

    /**Realizamos las operaciones necesarias para agregar nuevo libro*/
    private var resultadoAgregar =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            //validamos las respuesta de nuestroactivity si es ok o cancelado
            if (resultado.resultCode == Activity.RESULT_OK) {
                val mens = resultado.data?.getLongExtra(
                    resources.getString(R.string.id_libros_agregar),
                    0
                )//obteenmos el id del libro que se agrego
                Toast.makeText(
                    this, "GREGADO EXITOSAMENTE",
                    Toast.LENGTH_SHORT
                ).show()
                println("Datos titulo:${mens}")
                pushAgregarLibro = false
                agregarLibroListVew()//cargamos los nuevos datos a listvew
                adaptador.notifyDataSetChanged()
            }
            if (resultado.resultCode == Activity.RESULT_CANCELED) {
                pushAgregarLibro = false
                Toast.makeText(
                    this,
                    "CAMPOS VACIOS",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    var resultadoEditarLIbro =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "EDITADO EXITOSAMENTE", Toast.LENGTH_SHORT).show()
                agregarLibroListVew()
            }
            if (resultado.resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "ERRRO INTENTE DE NUEVO", Toast.LENGTH_SHORT).show()
            }
        }


    /**Funcion para actualizar listVew, con consula a base de datos SQLite*/
    private fun agregarLibroListVew() {
        lista.addAll(CrudBooks(applicationContext).leerLibro())
        adaptador = AdapaterLibrosVew(
            this,
            CrudBooks(applicationContext).leerLibro()
        )
        binding.listBooks.adapter = adaptador
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

    var posisionEditada: Int? = null
    override fun onContextItemSelected(item: MenuItem): Boolean {
        item
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        posisionEditada = info.position
        return when (item.itemId) {
            R.id.id_editar -> {
                val intento: Intent = Intent(this, EditarLIbroActivity::class.java)
                intento.putExtra(
                    "ID_LIBRO",
                    CrudBooks(this).leerLibro()[posisionEditada!!].idLibro
                )
                intento.putExtra(
                    "DATOS_LIBROS",
                    CrudBooks(this).leerLibro()[posisionEditada!!]
                )
                resultadoEditarLIbro.launch(intento)
                Toast.makeText(this, "Boton Editar", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.id_eliminar -> {
                Toast.makeText(
                    this,
                    "Eliminado ${
                        CrudBooks(this).borrarLibro(
                            CrudBooks(this).leerLibro()[posisionEditada!!].idLibro
                        )
                    }",
                    Toast.LENGTH_SHORT
                ).show()
                agregarLibroListVew()
                true
            }
            else -> {
                false
            }
        }
    }


    override fun onResume() {
        pushDetalleLibro = false
        super.onResume()
    }
}