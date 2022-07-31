package com.edolo.aplicacionlibro

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.edolo.aplicacionlibro.databinding.ActivityComunicacionBinding

class ComunicacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComunicacionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComunicacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btAbrirReceptor.setOnClickListener {
            val intento: Intent = Intent(this, ReceptorActivity::class.java)
            intento.putExtra(
                resources.getString(R.string.identificador),
                "RECEPTOR 0 DESDE COMUNICACION"
            )
            //startActivity(intento)
            resultadoLanzador.launch(intento)


        }
        binding.btAbrirReceptor1.setOnClickListener {
            val intento: Intent = Intent(this, ReceptorActivity1::class.java)
            intento.putExtra(
                resources.getString(R.string.identificador),
                "RECEPTOR 1 DESDE COMUNICACION"
            )
            //startActivity(intento)
            resultadoLanzador1.launch(intento)
        }
        binding.btAbrirReceptor2.setOnClickListener {
            val intento: Intent = Intent(this, ReceptorActivity2::class.java)
            intento.putExtra(
                resources.getString(R.string.identificador),
                "RECEPTOR 2 DESDE COMUNICACION"
            )
            // startActivity(intento)
            resultadoLanzador2.launch(intento)
        }


    }

    var resultadoLanzador =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == Activity.RESULT_OK) {
                val mens =
                    resultado.data?.getStringExtra(resources.getString(R.string.identificador_envio))
                        ?: ""
                Toast.makeText(this, mens, Toast.LENGTH_SHORT).show()
            }
        }
    var resultadoLanzador1 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == Activity.RESULT_OK) {
                val mens =
                    resultado.data?.getStringExtra(resources.getString(R.string.identificador_envio))
                        ?: ""
                Toast.makeText(this, mens, Toast.LENGTH_SHORT).show()
            }
        }
    var resultadoLanzador2 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == Activity.RESULT_OK) {

                val mens =
                    resultado.data?.getStringExtra(resources.getString(R.string.identificador_envio))
                        ?: ""
                Toast.makeText(this, mens, Toast.LENGTH_SHORT).show()
            }
            if (resultado.resultCode == Activity.RESULT_CANCELED){

            }
        }
}